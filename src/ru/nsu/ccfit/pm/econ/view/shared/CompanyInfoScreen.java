package ru.nsu.ccfit.pm.econ.view.shared;


import java.util.Map;

import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.LinkedList;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonListener;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

import com.google.inject.Inject;

public class CompanyInfoScreen extends GameTab implements Bindable, InjectionVisitable, 
	ITurnChangeListener, IEconomicActivityListener {

	static final Logger logger = LoggerFactory.getLogger(CompanyInfoScreen.class);

	@WTKX private ListButton companyLB;
	@WTKX private Label typeLabel;
	@WTKX private TextArea descriptionTA;
	@WTKX private Label accountingPeriodLabel;
	@WTKX private Label totalSharesLabel;
	@WTKX private Label marketValueLabel;
	@WTKX private Label totalMarketValueLabel;
	@WTKX private TableView incomeTable;

	/*
	 * Injectables.
	 */
	private IFormatter formatter;
	private PlayerRosterGateway playerRosterGw;
	private CompanyRosterGateway companyRosterGw;
	private GameTimeGateway gameTimeGw;
	private EconomicActivityGateway economicActivityGw;

	private Map<Integer, Long> companyIdByIndex = new java.util.HashMap<Integer, Long>();
	private Map<Long, List<HashMap<String, String>>> incomeReportsByCompany = new java.util.HashMap<Long, List<HashMap<String,String>>>();

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");

		installListeners();
	}

	@Inject
	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	@Inject
	public void setPlayerRosterGateway(PlayerRosterGateway gateway) {
		if (this.playerRosterGw != null) {
			logger.warn("Redefining playerRosterGateway");
		}

		this.playerRosterGw = gateway;
	}

	@Inject
	public void setCompanyRosterGateway(CompanyRosterGateway gateway) {
		if (this.companyRosterGw != null) {
			logger.warn("Redefining companyRosterGateway");
		}

		this.companyRosterGw = gateway;
	}

	@Inject
	public void setGameTimeGateway(GameTimeGateway gateway) {
		if (this.gameTimeGw != null) {
			logger.warn("Redefining companyRosterGateway");
			this.gameTimeGw.getTurnChangeListeners().remove(this);
		}

		this.gameTimeGw = gateway;
		this.gameTimeGw.getTurnChangeListeners().add(this);
	}

	@Inject
	public void setEconomicGateway(EconomicActivityGateway gateway) {
		if (this.economicActivityGw != null) {
			logger.warn("Redefining EconomicGateway");
			this.economicActivityGw.getEconomicActivityListeners().remove(this);
		}

		this.economicActivityGw = gateway;
		this.economicActivityGw.getEconomicActivityListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

	private void initScreenControls(boolean firstTime) {
		redrawListButton();
		redrawScreen();
		
		if (firstTime && companyLB.getListData().getLength() > 0) {
			companyLB.setSelectedIndex(0);
			logger.info("set");
		}
	}

	private void installListeners() {
		companyLB.getButtonListeners().add(new ButtonListener.Adapter() {

			@Override
			public void buttonDataChanged(Button arg0, Object arg1) {
				redrawScreen();
			}
		});
	}

	private void redrawListButton() {
		List<String> data = new LinkedList<String>();
		companyIdByIndex.clear();
		int cnt = 0;
		for (IUCompany company : companyRosterGw.getCompanyList()) {
			data.add(company.getName());
			companyIdByIndex.put(cnt, company.getId());
			cnt++;
		}
		
		int selectedIdx = companyLB.getSelectedIndex();
		companyLB.setListData(data);
		if (selectedIdx < companyLB.getListData().getLength())
			companyLB.setSelectedIndex(selectedIdx);
	}

	protected Long redrawScreen() {
		int idx = companyLB.getSelectedIndex();
		Long companyId = companyIdByIndex.get(idx);
		IUCompany company = getCompanyById(companyId);
		redrawDesription(company);
		redrawLabels(company);
		redrawTable(companyId);
		return companyId;
	}

	private void redrawTable(Long companyId) {
		List<HashMap<String, String>> incomeReport = incomeReportsByCompany.get(companyId);
		if (incomeReport == null) {
			incomeTable.setTableData(new LinkedList<HashMap<String, String>>());
		} else {
			incomeTable.setTableData(incomeReport);
		}
	}

	private void redrawLabels(IUCompany company) {
		typeLabel.setText(company != null ? company.getCompanyType() : "");
		accountingPeriodLabel.setText(company != null ? Integer.toString(company.getAccountingPeriod()) : "");
		totalSharesLabel.setText(company != null ? Integer.toString(company.getTotalSharesAmount()) : "");
		marketValueLabel.setText(company != null ? formatter.formatMoney(company.getShareMarketValue()) : "");
		totalMarketValueLabel.setText(company != null ? formatter.formatMoney(company.getShareMarketValue()*company.getTotalSharesAmount()) : "");
	}

	private void redrawDesription(IUCompany company) {
		descriptionTA.setText("");
		if (company == null)
			return;
		for (String line : company.getDescription().split("\n")) {
			int insPoint = descriptionTA.getDocument().getCharacterCount() - 1;
			descriptionTA.setSelection(insPoint, 0);
			descriptionTA.insertText(line);
			descriptionTA.insertParagraph();
		}
	}

	private IUCompany getCompanyById(Long companyId) {
		if (companyId == null)
			return null;
		return companyRosterGw.getCompanyById(companyId);
	}

	private void companyIncomeReportArrived(IUCompany company) {
		long companyId = company.getId();
		List<HashMap<String, String>> data = incomeReportsByCompany.get(companyId);
		if (data == null) {
			data = new LinkedList<HashMap<String, String>>();
			incomeReportsByCompany.put(companyId, data);
		}
		HashMap<String, String> element = new HashMap<String, String>();
		element.put("turn", Integer.toString((data.getLength() + 1) * company.getAccountingPeriod()));
		double profit = company.getExpectedProfit();
		double ratio = company.getDividendPayoutRatio();
		element.put("income", formatter.formatMoney(profit));
		element.put("ratio", Double.toString(ratio));
		element.put("payout", formatter.formatMoney(profit*ratio));
		data.add(element);
	}

	@Override
	public void onGameStart() {
		logger.info("onGameStart");
		initScreenControls(true);
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
		initScreenControls(false);
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
		if (!isFinished) return;
		int turnNumber = gameTimeGw.getTurnNumber();
		for (IUCompany company : companyRosterGw.getCompanyList()){
			if ((turnNumber%company.getAccountingPeriod()) == 0){
				companyIncomeReportArrived(company);
			}
		}
		redrawScreen();
	}

	@Override
	public void onDramaticTurnShareMarketValueChange(IUCompany company, double valueChange, double valueChangePrecentage) {
	}

	@Override
	public void onShareMarketValueChange(IUCompany company, double newShareMarketValue) {
		redrawScreen();
	}

	@Override
	public void onStockIndexChange(double newStockIndex) {
	}
}
