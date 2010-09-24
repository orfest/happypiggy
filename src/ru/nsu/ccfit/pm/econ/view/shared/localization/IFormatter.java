package ru.nsu.ccfit.pm.econ.view.shared.localization;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;

/**
 * Provider of string formatting operations.
 * @author dragonfly
 */
public interface IFormatter {
	
	String formatTurn(@Nonnegative int turnNumber);
	
	String formatMoney(double moneyAmount);
	
	String formatShares(@Nonnegative int numShares);
	
	String formatSharesWithCost(@Nonnegative int numShares, @Nonnegative double cost);

	String formatCompanyNameWithType(@Nonnull String name, @Nonnull String type);

	String formatChatMessage(boolean isPublic, IUGameTime receiveTime, String who, String message);

	String formatGameTime(@Nonnull IUGameTime gameTime);

	String formatNewsSummary(int allnews, int unreadnews);

	String formatNewsType(CompanyMessageType type);

	String formatNewsEffect(double cCoefficient, double kCoefficient);

	String formatNewsTitle(@Nonnull String text);
}
