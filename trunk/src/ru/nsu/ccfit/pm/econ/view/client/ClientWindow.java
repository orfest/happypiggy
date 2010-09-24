package ru.nsu.ccfit.pm.econ.view.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.view.shared.BuyScreen;
import ru.nsu.ccfit.pm.econ.view.shared.ChatScreen;
import ru.nsu.ccfit.pm.econ.view.shared.CompanyInfoScreen;
import ru.nsu.ccfit.pm.econ.view.shared.GameTab;
import ru.nsu.ccfit.pm.econ.view.shared.MineStatsGateway;
import ru.nsu.ccfit.pm.econ.view.shared.NewsScreen;
import ru.nsu.ccfit.pm.econ.view.shared.RatingScreen;
import ru.nsu.ccfit.pm.econ.view.shared.SellScreen;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.Localization;

public class ClientWindow extends Window implements Bindable, InjectionVisitable, IPlayerStatsUpdateListener {
	
	static final Logger logger = LoggerFactory.getLogger(ClientWindow.class);
	
	private static final String RES_TITLE = "c.windowTitle";
	private static final String RES_STUDENT_ROLE = "roles.student";
	private static final String RES_TEACHER_ROLE = "roles.teacher";
	private static final String RES_UNKNOWN_ROLE = "roles.unknown";
	
	/*
	 * Injectables.
	 */
	private Resources translations;
	private MineStatsGateway gateway;
	
	/*
	 * Child elements.
	 */
	@WTKX private Header header;
	@WTKX private LoginSheet loginSheet;
	@WTKX private ChatScreen chatScreen;
	@WTKX private NewsScreen newsScreen;
	@WTKX private BuyScreen buyScreen;
	@WTKX private SellScreen sellScreen;
	@WTKX private CompanyInfoScreen companyInfoScreen;
	@WTKX private RatingScreen ratingScreen;
	@WTKX private FinancesScreen financesScreen;
	private List<InjectionVisitable> visitableChildren = new ArrayList<InjectionVisitable>();
	private List<GameTab> gameScreens = new ArrayList<GameTab>();

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
		
		gameScreens.add(checkNotNull(chatScreen));
		gameScreens.add(checkNotNull(newsScreen));
		gameScreens.add(checkNotNull(buyScreen));
		gameScreens.add(checkNotNull(sellScreen));
		gameScreens.add(checkNotNull(companyInfoScreen));
		gameScreens.add(checkNotNull(ratingScreen));
		gameScreens.add(checkNotNull(financesScreen));
		// other game screens should be added to list here 
		
		visitableChildren.add(checkNotNull(header));
		visitableChildren.add(checkNotNull(loginSheet));
		visitableChildren.addAll(gameScreens);
		// other child elements should be added to visitable list here
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
		for (InjectionVisitable child : visitableChildren)
			child.acceptInjectionVisitor(visitor);
	}

	@Override
	public void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue) {
	}

	@Override
	public void onRoleDetermined(IUPlayer player) {
		String roleKey;
		if (player instanceof IUStudent) {
			roleKey = RES_STUDENT_ROLE;
		} else if (player instanceof IUTeacher) {
			roleKey = RES_TEACHER_ROLE;
		} else {
			logger.warn("Unknown player role: {}", player);
			roleKey = RES_UNKNOWN_ROLE;
		}
		
		String playerName = player.getUnmodifiablePersonDescription().getName();
		String nameString = translations.getString(roleKey) + ": " + playerName;
		String title = translations.getString(RES_TITLE) + " - " + nameString;
		setTitle(title);
	}

	@Override
	public void onSharesChange(IUShareholder player,
			IUShareHolding updatedShareHolding, int addedAmount,
			double subtractedMoney) {
	}
	
	@Inject
	public void setLocalization(Localization localization) {
		this.translations = localization.getResources();
	}

	@Inject
	public void setMineStatsGateway(MineStatsGateway gateway) {
		if (this.gateway != null) {
			logger.warn("Redefining mineStatsGateway");
			this.gateway.getPlayerStatsUpdateListeners().remove(this);
		}
		
		this.gateway = gateway;
		this.gateway.getPlayerStatsUpdateListeners().add(this);
	}

}
