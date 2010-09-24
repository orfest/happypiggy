package ru.nsu.ccfit.pm.econ.view.shared.localization;

import org.apache.pivot.util.Resources;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;

import com.google.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class Formatter implements IFormatter {

	private static final String LOCALIZATION_FORMAT_PREFIX = "format.";
	private static final String FMT_MONEY = "currency";
	private static final String FMT_SHARES = "shares";
	private static final String FMT_TURN = "turn";
	private static final String FMT_SHARES_WITH_COST = "sharesWithCost";
	private static final String FMT_NEWS_ALL_UNREAD = "newsSummary";
	private static final String FMT_NEWS_OFFICIAL = "newsOfficial";
	private static final String FMT_NEWS_RUMOR = "newsRumor";
	private static final String FMT_NEWS_TITLE = "newsTitle";
	private static final String FMT_NEWS_EFFECT = "newsEffect";

	/*
	 * Injectables.
	 */
	private Localization localization;
	private Resources formats;
	private IULocalState local;

	@Inject
	public void setLocalization(Localization localization) {
		this.localization = localization;
		this.formats = this.localization.getResources();
	}

	@Inject
	public void setLocalState(IULocalState local) {
		this.local = local;
	}

	private String getFormat(String fmtId) {
		return formats.getString(LOCALIZATION_FORMAT_PREFIX + fmtId);
	}

	@Override
	public String formatMoney(double moneyAmount) {
		IUScenarioProperties scenarioProps = local.getScenarioProperties();
		if (scenarioProps != null) {
			String shortCurrency = checkNotNull(scenarioProps.getShortCurrencyName());

			if (shortCurrency.indexOf("%") < 0) {
				return String.format("%.2f %s", moneyAmount, shortCurrency);
			} else {
				return String.format(shortCurrency, moneyAmount);
			}
		}

		return String.format(getFormat(FMT_MONEY), moneyAmount);
	}

	@Override
	public String formatShares(int numShares) {
		return String.format(getFormat(FMT_SHARES), numShares);
	}

	@Override
	public String formatTurn(int turnNumber) {
		return String.format(getFormat(FMT_TURN), turnNumber);
	}

	@Override
	public String formatSharesWithCost(int numShares, double cost) {
		String sharesString = formatShares(numShares);
		String moneyString = formatMoney(cost);
		return String.format(getFormat(FMT_SHARES_WITH_COST), sharesString, moneyString);
	}

	@Override
	public String formatCompanyNameWithType(String name, String type) {
		return String.format("'%s', %s", name, type);
	}

	@Override
	public String formatChatMessage(boolean isPublic, IUGameTime receiveTime, String who, String message) {
		return String.format("%s%s - %s: %s", (isPublic ? "" : "***** "), formatGameTime(receiveTime), who, message);
	}

	@Override
	public String formatGameTime(IUGameTime gameTime) {
		return String.format("[%1$d] %2$tM.%2$tS", gameTime.getTurnNumber(), gameTime.getTime());
	}

	@Override
	public String formatNewsSummary(int allnews, int unreadnews) {
		return String.format(getFormat(FMT_NEWS_ALL_UNREAD), allnews, unreadnews);
	}

	@Override
	public String formatNewsType(CompanyMessageType type) {
		if (type == CompanyMessageType.OFFICIAL) {
			return getFormat(FMT_NEWS_OFFICIAL);
		} else if (type == CompanyMessageType.RUMOR) {
			return getFormat(FMT_NEWS_RUMOR);
		} else {
			return null;
		}
	}

	@Override
	public String formatNewsEffect(double cCoefficient, double kCoefficient) {
		char sign = (cCoefficient >= 0) ? '+' : '-';
		String moneyFormatted = formatMoney(Math.abs(cCoefficient));
		return String.format(getFormat(FMT_NEWS_EFFECT), sign, moneyFormatted, kCoefficient);
	}

	@Override
	public String formatNewsTitle(String text) {
		return String.format(getFormat(FMT_NEWS_TITLE), text);
	}

}
