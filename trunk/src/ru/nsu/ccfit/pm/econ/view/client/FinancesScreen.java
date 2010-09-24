package ru.nsu.ccfit.pm.econ.view.client;

import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.view.shared.CompanyRosterGateway;
import ru.nsu.ccfit.pm.econ.view.shared.GameTab;
import ru.nsu.ccfit.pm.econ.view.shared.GameTimeGateway;
import ru.nsu.ccfit.pm.econ.view.shared.MineStatsGateway;
import ru.nsu.ccfit.pm.econ.view.shared.PlayerRosterGateway;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

import com.google.inject.Inject;

public class FinancesScreen extends GameTab 
	implements Bindable, InjectionVisitable, ITurnChangeListener, IPlayerStatsUpdateListener {
	
	static final Logger logger = LoggerFactory.getLogger(FinancesScreen.class);
	
	static final String ROW_HEADER_FIELD = "category";
	static final String TURN_FIELD_PREFIX = "turn";
	static final int DEFAULT_COLUMN_WIDTH = 80;
	
	/*
	 * WTKX components.
	 */
	@WTKX private TableView balanceTable;
	@WTKX private TableView transactionTable;
	@WTKX private Label moneyLabel;
	
	/*
	 * Balance table rows.
	 */
	@WTKX private FinancesScreenTurnRow moneyOnTurnStartTR;
	@WTKX private FinancesScreenTurnRow takenLoansTR;
	@WTKX private FinancesScreenTurnRow takenDepositsTR;
	@WTKX private FinancesScreenTurnRow boughtSharesTR;
	@WTKX private FinancesScreenTurnRow soldSharesTR;
	@WTKX private FinancesScreenTurnRow dividendsTR;
	
	/*
	 * Transaction table rows.
	 */
	@WTKX private FinancesScreenTurnRow paymentsOnLoansTR;
	@WTKX private FinancesScreenTurnRow paymentsOnDepositsTR;

	/*
	 * Injectables.
	 */
	private PlayerRosterGateway playerRosterGw;
	private CompanyRosterGateway companyRosterGw;
	private GameTimeGateway gameTimeGw;
	private MineStatsGateway mineStatsGw;
	private IFormatter formatter;
	
	private boolean initialized = false;
	private double sharesSpendings = 0;
	private double sharesIncome = 0;

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
	
	private void installListeners() {
		// No listeners!!!!
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}
	
	@Override
	public void onGameStart() {
		if (!initialized){
			for (;removeNewTurnBalanceColumn(););
			for (;removeNewTurnTransactionColumn(););
			if (addNewTurnBalanceColumn(1)){
				addNewTurnTransactionColumn(1);
				initialized = true;
			}
		}
		logger.info("onGameStart");
	}

	@Override
	public void onTurnNumberChange(int turnNumber) {
		logger.info("onTurnNumberChange"+turnNumber);
		sharesIncome = sharesSpendings = 0.;
		if (!initialized){
			for (;removeNewTurnBalanceColumn(););
			for (;removeNewTurnTransactionColumn(););
			if (addNewTurnBalanceColumn(turnNumber)){
				addNewTurnTransactionColumn(turnNumber);
				initialized = true;
			}
		} else {
			addNewTurnBalanceColumn(turnNumber);
			addNewTurnTransactionColumn(turnNumber);
		}
		logger.info("~onTurnNumberChange");
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
		if (!isFinished) {
			return;
		}
	}

	@Override
	public void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue) {
		moneyLabel.setText(formatter.formatMoney(cashValue));
	}

	@Override
	public void onRoleDetermined(IUPlayer player) {
		// no action required
	}

	@Override
	public void onSharesChange(IUShareholder player, IUShareHolding updatedShareHolding, 
			int addedAmount, double subtractedMoney) {
		if (subtractedMoney <= 0){
			sharesIncome -= subtractedMoney;
		} else {
			sharesSpendings += subtractedMoney;
		}
		updateBalanceColumn();
	}
	
	private String columnName(int turnNumber) {
		return TURN_FIELD_PREFIX + turnNumber;
	}
	
	private TableView.Column createTurnColumn(int turnNumber) {
		return new TableView.Column(columnName(turnNumber), 
				formatter.formatTurn(turnNumber), DEFAULT_COLUMN_WIDTH);
	}
	
	private void updateBalanceColumn() {
		boughtSharesTR.changeLastTurnValue(-sharesSpendings);
		soldSharesTR.changeLastTurnValue(sharesIncome);
	}
	
	private boolean addNewTurnBalanceColumn(int turnNumber) {
		IUPlayer player = playerRosterGw.getMe();
		if (!(player instanceof IULimitedBudgetPlayer)) {
			logger.warn("Finances screen opened on client without cash capabilities");
			return false;
		}
		
		moneyOnTurnStartTR.addTurnValue(turnNumber, ((IULimitedBudgetPlayer)(playerRosterGw.getMe())).getCash());
		takenLoansTR.addTurnValue(turnNumber, 0);
		takenDepositsTR.addTurnValue(turnNumber, 0);
		boughtSharesTR.addTurnValue(turnNumber, -sharesSpendings);
		soldSharesTR.addTurnValue(turnNumber, sharesIncome);
		dividendsTR.addTurnValue(turnNumber, 0);
		balanceTable.getColumns().add(createTurnColumn(turnNumber));
		return true;
	}
	
	private void addNewTurnTransactionColumn(int turnNumber) {
		paymentsOnLoansTR.addTurnValue(turnNumber, 0);
		paymentsOnDepositsTR.addTurnValue(turnNumber, 0);
		transactionTable.getColumns().add(createTurnColumn(turnNumber));
	}
	
	private boolean removeNewTurnTransactionColumn() {
		TableView.Column col = transactionTable.getColumns().get(1);
		if (col != null){
			transactionTable.getColumns().remove(col);
			return true;
		}
		return false;
	}

	private boolean removeNewTurnBalanceColumn() {
		TableView.Column col = balanceTable.getColumns().get(1);
		if (col != null){
			balanceTable.getColumns().remove(col);
			return true;
		}
		return false;
	}
}
