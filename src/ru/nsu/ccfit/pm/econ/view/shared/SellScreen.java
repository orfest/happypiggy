package ru.nsu.ccfit.pm.econ.view.shared;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.LinkedList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Span;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewSelectionListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.TextInputTextListener;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.common.view.IBuyOfferListener;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;
import ru.nsu.ccfit.pm.econ.engine.data.ShareHoldingEngine;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

import com.google.gag.annotation.remark.Facepalm;
import com.google.inject.Inject;

public class SellScreen extends GameTab implements Bindable, InjectionVisitable, 
	ITurnChangeListener, IBuyOfferListener, IPlayerStatsUpdateListener, IEconomicActivityListener {

	static final Logger logger = LoggerFactory.getLogger(SellScreen.class);

	@WTKX private TableView offerTable;
	@WTKX private PushButton cancelOfferPB;
	@WTKX private TableView companyTable;
	@WTKX private CompanyInfoForm companyInfoForm;
	@WTKX private TextInput numSharesTI;
	@WTKX private TextInput shareCostTI;
	@WTKX private TextInput totalCostTI;
	@WTKX private PushButton offerPB;

	/*
	 * Injectables.
	 */
	private IFormatter formatter;
	private PlayerRosterGateway playerRosterGw;
	private CompanyRosterGateway companyRosterGw;
	private GameTimeGateway gameTimeGw;
	private BuySellGateway buySellGw;
	private MineStatsGateway mineStatsGw;
	private EconomicActivityGateway economicActivityGw;

	private boolean textInputUpdating = false;
	private java.util.Map<Integer, Long> companyByIndex = new java.util.HashMap<Integer, Long>();
	private java.util.Map<Integer, IUBuyOffer> offerByIndex = new java.util.HashMap<Integer, IUBuyOffer>();
	private java.util.List<IUBuyOffer> myOffers = new java.util.LinkedList<IUBuyOffer>();

	private long lastUpdated = -1;

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
			logger.warn("Redefining gameTimeGateway");
			this.gameTimeGw.getTurnChangeListeners().remove(this);
		}

		this.gameTimeGw = gateway;
		this.gameTimeGw.getTurnChangeListeners().add(this);
	}

	@Inject
	public void setBuySellGateway(BuySellGateway gateway) {
		if (this.buySellGw != null) {
			logger.warn("Redefining buySellGateway");
			this.buySellGw.getBuyOfferListeners().remove(this);
		}

		this.buySellGw = gateway;
		this.buySellGw.getBuyOfferListeners().add(this);
	}

	@Inject
	public void setEconomicActivityGateway(EconomicActivityGateway gateway) {
		if (this.economicActivityGw != null) {
			logger.warn("Redefining economicActivityGw");
			this.economicActivityGw.getEconomicActivityListeners().remove(this);
		}

		this.economicActivityGw = gateway;
		this.economicActivityGw.getEconomicActivityListeners().add(this);
	}

	@Inject
	public void setMineStatsGateway(MineStatsGateway gateway) {
		if (this.mineStatsGw != null) {
			logger.warn("Redefining mineStatsGateway");
			this.mineStatsGw.getPlayerStatsUpdateListeners().remove(this);
		}

		this.mineStatsGw = gateway;
		this.mineStatsGw.getPlayerStatsUpdateListeners().add(this);
	}

	@Inject
	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");

		installListeners();
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
		checkNotNull(companyInfoForm).acceptInjectionVisitor(visitor);
	}

	private void installListeners() {
		offerTable.getTableViewSelectionListeners().add(new TableViewSelectionListener.Adapter() {

			@Override
			public void selectedRangesChanged(TableView arg0, Sequence<Span> arg1) {
				updateOffersClicked();
			}
		});

		cancelOfferPB.getButtonPressListeners().add(new ButtonPressListener() {

			@Override
			public void buttonPressed(Button arg0) {
				cancelOffer();
			}
		});

		companyTable.getTableViewSelectionListeners().add(new TableViewSelectionListener.Adapter() {

			@Override
			public void selectedRangesChanged(TableView arg0, Sequence<Span> arg1) {
				updateCompaniesClicked();
			}
		});

		offerPB.getButtonPressListeners().add(new ButtonPressListener() {

			@Override
			public void buttonPressed(Button arg0) {
				addOffer();
			}
		});
		
		numSharesTI.getTextInputTextListeners().add(new TextInputTextListener() {
			
			@Override
			public void textChanged(TextInput arg0) {
				shareCostChanged();
			}
		});

		shareCostTI.getTextInputTextListeners().add(new TextInputTextListener() {

			@Override
			public void textChanged(TextInput arg0) {
				shareCostChanged();
			}
		});

		totalCostTI.getTextInputTextListeners().add(new TextInputTextListener() {

			@Override
			public void textChanged(TextInput arg0) {
				totalCostChanged();
			}
		});
	}

	private void initScreenControls() {
		redrawCompanies();
		redrawOffers();
		this.updateCompaniesClicked();
		this.updateOffersClicked();
	}

	protected void totalCostChanged() {
		if (textInputUpdating)
			return;
		textInputUpdating = true;
		updateAnotherTextInput(totalCostTI.getText(), shareCostTI, true);
		textInputUpdating = false;
	}

	protected void shareCostChanged() {
		if (textInputUpdating)
			return;
		textInputUpdating = true;
		updateAnotherTextInput(shareCostTI.getText(), totalCostTI, false);
		textInputUpdating = false;
	}

	private void updateAnotherTextInput(String text, TextInput textInput, boolean division) {
		textInput.setText("");
		Long amount = null;
		Double cost = null;
		try {
			amount = Long.parseLong(numSharesTI.getText());
			cost = Double.parseDouble(text);
		} catch (NumberFormatException e) {
		}
		if (amount == null || amount <= 0 || cost == null || cost <= 0.)
			return;
		double anotherAmount = division ? cost / amount : cost * amount;
		textInput.setText(Double.toString(anotherAmount));
	}

	protected void addOffer() {
		Integer amount = null;
		Double cost = null;
		Long companyId = null;
		try {
			amount = Integer.parseInt(numSharesTI.getText());
			cost = Double.parseDouble(totalCostTI.getText());
			int idx = companyTable.getSelectedIndex();
			if (idx >= 0) {
				companyId = companyByIndex.get(idx);
			}
		} catch (NumberFormatException e) {
		}
		if (amount == null || amount <= 0 || cost == null || cost <= 0. || companyId == null)
			return;
		long playerId = playerRosterGw.getMe().getId();

		IUBuyOffer offer = new BuyOfferEngine(playerId, new ShareHoldingEngine(amount, companyId, playerId), cost);
		buySellGw.addBuyOffer(offer);
	}

	protected void cancelOffer() {
		int idx = offerTable.getSelectedIndex();
		IUBuyOffer offer = offerByIndex.get(idx);
		if (offer == null)
			return;
		buySellGw.cancelBuyOffer(offer);
	}

	protected void updateCompaniesClicked() {
		int idx = companyTable.getSelectedIndex();
		Long companyId = companyByIndex.get(idx);
		if (companyId == null) {
			companyInfoForm.update(null, 0, null);
			updateForm(-1);
			return;
		}
		updateForm(companyId);
	}

	protected void updateOffersClicked() {
		int idx = offerTable.getSelectedIndex();
		IUBuyOffer offer = offerByIndex.get(idx);
		if (offer == null) {
			updateForm(-1);
			companyInfoForm.update(null, 0, null);
			return;
		}
		long companyId = offer.getShareHolding().getCompanyId();
		updateForm(companyId);
	}

	private void updateForm(long companyId) {
		IUCompany company = getCompanyById(companyId);
		companyInfoForm.update(company, company != null ? getMineShares(company) : 0, formatter);
		lastUpdated = companyId;
	}

	@Facepalm
	private void updateForm() {
		updateForm(lastUpdated);
	}

	private void redrawOffers() {
		offerByIndex.clear();
		int cnt = 0;
		List<HashMap<String, String>> data = new LinkedList<HashMap<String, String>>();
		offerTable.setTableData(data);
		for (IUBuyOffer offer : myOffers) {
			HashMap<String, String> element = new HashMap<String, String>();
			IUCompany company = getCompanyById(offer.getShareHolding().getCompanyId());
			if (company == null)
				continue;
			element.put("company", formatter.formatCompanyNameWithType(company.getName(), company.getCompanyType()));
			element.put("offer", formatter.formatSharesWithCost(offer.getShareHolding().getAmount(), offer
					.getSuggestedValue()));
			data.add(element);
			offerByIndex.put(cnt++, offer);
		}
		offerTable.setTableData(data);
	}

	private void redrawCompanies() {
		List<HashMap<String, String>> data = new LinkedList<HashMap<String, String>>();
		int cnt = 0;
		for (IUCompany company : companyRosterGw.getCompanyList()) {
			long companyId = company.getId();
			HashMap<String, String> element = new HashMap<String, String>();
			element.put("company", formatter.formatCompanyNameWithType(company.getName(), company.getCompanyType()));
			element.put("mine", Integer.toString(getMineShares(company)));
			element.put("cost", formatter.formatMoney(company.getShareMarketValue()));
			data.add(element);
			companyByIndex.put(cnt, companyId);
			cnt++;
		}
		companyTable.setTableData(data);
	}

	private int getMineShares(IUCompany selectedCompany) {
		IUPlayer player = playerRosterGw.getMe();
		if (!(player instanceof IUShareholder))
			return 0;
		
		Collection<? extends IUShareHolding> shares = ((IUShareholder) player).getUnmodifiableShareList();
		int result = 0;
		for (IUShareHolding sh : shares) {
			if (sh.getCompanyId() != selectedCompany.getId())
				continue;
			result += sh.getAmount();
		}
		return result;
	}

	private IUCompany getCompanyById(long companyId) {
		return companyRosterGw.getCompanyById(companyId);
	}

	@Override
	public void onGameStart() {
		logger.info("onGameStart");
		initScreenControls();
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
		initScreenControls();
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
		updateForm();
	}

	@Override
	public void onBuyOffersUpdated(long companyId, Collection<? extends IUBuyOffer> updatedOffers) {
		updateForm();
	}

	@Override
	public void onMineBuyOffersChanged(Collection<? extends IUBuyOffer> mineOffers) {
		myOffers = new java.util.LinkedList<IUBuyOffer>(mineOffers);
		redrawOffers();
	}

	@Override
	public void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue) {
		updateForm();
	}

	@Override
	public void onRoleDetermined(IUPlayer player) {
	}

	@Override
	public void onSharesChange(IUShareholder player, IUShareHolding updatedShareHolding, int addedAmount, double subtractedMoney){
		redrawCompanies();
		redrawOffers();
		updateForm();
	}

	@Override
	public void onDramaticTurnShareMarketValueChange(IUCompany company, double valueChange, double valueChangePrecentage) {
		redrawCompanies();
		updateForm();
	}

	@Override
	public void onShareMarketValueChange(IUCompany company, double newShareMarketValue) {
		redrawCompanies();
		updateForm();
	}

	@Override
	public void onStockIndexChange(double newStockIndex) {
		redrawCompanies();
		updateForm();
	}
}
