package ru.nsu.ccfit.pm.econ.view.shared;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.ListViewSelectionListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Span;
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
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

import com.google.inject.Inject;

public class BuyScreen extends GameTab implements Bindable, InjectionVisitable, 
	IBuyOfferListener, ITurnChangeListener, IPlayerStatsUpdateListener, IEconomicActivityListener {

	static final Logger logger = LoggerFactory.getLogger(BuyScreen.class);

	/*
	 * WTKX components.
	 */
	@WTKX private ListView companyList;
	@WTKX private ListView offerList;
	@WTKX private CompanyInfoForm companyInfoForm;
	@WTKX private Label offerLabel;
	@WTKX private Label costLabel;
	@WTKX private PushButton buyPB;

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
	
	private Map<Long, List<IUBuyOffer>> offersByCompany = new HashMap<Long, List<IUBuyOffer>>();
	private Map<Integer, Long> companyIdByIndex = new HashMap<Integer, Long>();
	private Map<Integer, IUBuyOffer> offerByIndex = new HashMap<Integer, IUBuyOffer>();

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
	public void setMineStatsGateway(MineStatsGateway gateway) {
		if (this.mineStatsGw != null) {
			logger.warn("Redefining mineStatsGateway");
			this.mineStatsGw.getPlayerStatsUpdateListeners().remove(this);
		}

		this.mineStatsGw = gateway;
		this.mineStatsGw.getPlayerStatsUpdateListeners().add(this);
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

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
		checkNotNull(companyInfoForm).acceptInjectionVisitor(visitor);
	}

	private void installListeners() {

		companyList.getListViewSelectionListeners().add(new ListViewSelectionListener.Adapter() {

			@Override
			public void selectedRangesChanged(ListView arg0, Sequence<Span> arg1) {
				updateCompaniesClicked();
			}
		});

		offerList.getListViewSelectionListeners().add(new ListViewSelectionListener.Adapter() {

			@Override
			public void selectedRangesChanged(ListView arg0, Sequence<Span> arg1) {
				updateOffersClicked();
			}
		});

		buyPB.getButtonPressListeners().add(new ButtonPressListener() {

			@Override
			public void buttonPressed(Button arg0) {
				buyButtonPressed();
			}
		});
	}

	private void initScreenControls() {
		redrawCompanies();
		updateCompaniesClicked();
	}

	private void redrawCompanies() {
		org.apache.pivot.collections.List<String> data = new org.apache.pivot.collections.LinkedList<String>();
		companyIdByIndex.clear();
		int cnt = 0;
		for (IUCompany company : companyRosterGw.getCompanyList()) {
			long companyId = company.getId();
			data.add(company.getName());
			companyIdByIndex.put(cnt, companyId);
			cnt++;
		}
		companyList.setListData(data);
	}

	protected void updateCompaniesClicked() {
		recalcOffersTable();
		updateOffersClicked();
		updateCompanyFrame();
	}

	protected void updateOffersClicked() {
		offerLabel.setText("");
		costLabel.setText("");
		IUBuyOffer selectedOffer = getSelectedBuyOffer();
		if (selectedOffer == null) {
			return;
		}
		double cost = selectedOffer.getSuggestedValue();
		int amount = selectedOffer.getShareHolding().getAmount();
		double pricePerShare = cost / amount;
		offerLabel.setText(formatter.formatSharesWithCost(amount, cost));
		costLabel.setText(formatter.formatMoney(pricePerShare));
	}

	protected void buyButtonPressed() {
		int idx = companyList.getSelectedIndex();
		Long companyId = companyIdByIndex.get(idx);
		IUBuyOffer offer = getSelectedBuyOffer();
		if (offer == null || companyId == null)
			return;
		buyRequest(offer);
	}

	private void updateCompanyFrame() {
		companyInfoForm.update(null, 0, null);
		int idx = companyList.getSelectedIndex();
		Long companyId = companyIdByIndex.get(idx);
		if (companyId == null){
			return;
		}
		IUCompany selectedCompany = getCompanyById(companyId);
		int mineShares = getMineShares(selectedCompany);
		companyInfoForm.update(selectedCompany, mineShares, formatter);
	}

	private void recalcOffersTable() {
		int idx = companyList.getSelectedIndex();
		Long companyId = companyIdByIndex.get(idx);
		org.apache.pivot.collections.List<String> data = new org.apache.pivot.collections.LinkedList<String>();
		offerByIndex.clear();
		int cnt = 0;
		List<IUBuyOffer> list = offersByCompany.get(companyId);
		if (list != null) {
			for (IUBuyOffer offer : list) {
				offerByIndex.put(cnt, offer);
				data.add(buildOfferString(offer));
				cnt++;
			}
		}
		offerList.setListData(data);
	}

	private String buildOfferString(IUBuyOffer offer) {
		int numShares = offer.getShareHolding().getAmount();
		double cost = offer.getSuggestedValue();
		return formatter.formatSharesWithCost(numShares, cost);
	}

	private IUBuyOffer getSelectedBuyOffer() {
		int idx = offerList.getSelectedIndex();
		return offerByIndex.get(idx);
	}

	private int getMineShares(IUCompany selectedCompany) {
		IUPlayer player = playerRosterGw.getMe();
		if (!(player instanceof IUShareholder))
			return 0;
		
		Collection<? extends IUShareHolding> shares = ((IUShareholder)player).getUnmodifiableShareList();
		int result = 0;
		for (IUShareHolding sh : shares){
			if (sh.getCompanyId() != selectedCompany.getId()) continue;
			result += sh.getAmount();
		}
		return result;
	}

	private IUCompany getCompanyById(long companyId) {
		return companyRosterGw.getCompanyById(companyId);
	}

	private void buyRequest(IUBuyOffer offer) {
		buySellGw.buy(offer);
	}

	@Override
	public void onBuyOffersUpdated(long companyId, Collection<? extends IUBuyOffer> updatedOffers) {
		List<IUBuyOffer> offers = new LinkedList<IUBuyOffer>(updatedOffers);
		offersByCompany.put(companyId, offers);
		recalcOffersTable();
	}

	@Override
	public void onMineBuyOffersChanged(Collection<? extends IUBuyOffer> mineOffers) {
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
	}

	@Override
	public void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue) {
	}

	@Override
	public void onRoleDetermined(IUPlayer player) {
	}

	@Override
	public void onSharesChange(IUShareholder player, IUShareHolding updatedShareHolding, int addedAmount, double subtractedMoney) {
		updateCompanyFrame();
	}

	@Override
	public void onDramaticTurnShareMarketValueChange(IUCompany company, double valueChange, double valueChangePrecentage) {
	}

	@Override
	public void onShareMarketValueChange(IUCompany company, double newShareMarketValue) {
		updateCompanyFrame();
	}

	@Override
	public void onStockIndexChange(double newStockIndex) {
	}
}
