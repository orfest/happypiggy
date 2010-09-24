package ru.nsu.ccfit.pm.econ.controller.scenario.persistence;
import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.persistence.IScenarioDAO;
import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.controller.scenario.Scenario;
import ru.nsu.ccfit.pm.econ.net.engine.data.Company;
import ru.nsu.ccfit.pm.econ.net.engine.data.CompanyMessage;

/**
 * @author orfest
 * 
 */
public class ScenarioDAO implements IScenarioDAO {

	private long companyId = 1;

	/**
	 * 
	 */
	public ScenarioDAO() {
	}

	@Override
	public IUScenario loadScenario(File file) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		Element scenarioElement = doc.getDocumentElement();
		if (scenarioElement == null || !scenarioElement.getNodeName().equals("scenario")) {
			return null;
		}

		return parseScenario(scenarioElement);
	}

	private IUScenario parseScenario(Element scenarioElement) {
		boolean name = false;
		boolean description = false;
		boolean currency_short_name = false;
		boolean currency_long_name = false;
		boolean author = false;
		boolean start_cash = false;
		boolean turn_length = false;

		LinkedList<IUCompany> companies = new LinkedList<IUCompany>();
		Scenario scenario = new Scenario();
		for (Node child = scenarioElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			String nodeName = child.getNodeName();
			String stringNodeValue = child.getTextContent();
			if (nodeName.equals("name")) {
				if (name)
					return null;
				name = true;
				scenario.setName(stringNodeValue);
			} else if (nodeName.equals("description")) {
				if (description)
					return null;
				description = true;
				scenario.setDescription(stringNodeValue);
			} else if (nodeName.equals("currency_short_name")) {
				if (currency_short_name)
					return null;
				currency_short_name = true;
				scenario.setShortCurrencyName(stringNodeValue);
			} else if (nodeName.equals("currency_long_name")) {
				if (currency_long_name)
					return null;
				currency_long_name = true;
				scenario.setFullCurrencyName(stringNodeValue);
			} else if (nodeName.equals("author")) {
				if (author)
					return null;
				author = true;
				scenario.setAuthor(stringNodeValue);
			} else if (nodeName.equals("start_cash")) {
				if (start_cash)
					return null;
				start_cash = true;
				scenario.setStartingCashValue(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("turn_length")) {
				if (turn_length)
					return null;
				turn_length = true;
				scenario.setTurnLengthMinutes(Integer.parseInt(stringNodeValue));
			} else if (nodeName.equals("comp_list")) {
				IUCompany comp = parseCompany(child);
				if (comp == null)
					return null;
				companies.add(comp);
			}
		}
		if (!name || !currency_short_name || !currency_long_name || !start_cash || !turn_length || companies.isEmpty())
			return null;
		scenario.setCompanies(companies);
		return scenario;
	}

	private IUCompany parseCompany(Node companyNode) {
		boolean name = false;
		boolean company_type = false;
		boolean description = false;
		boolean teacher_share_part = false;
		boolean total_share_number = false;
		boolean share_market_value = false;
		boolean expected_profit = false;
		boolean dividend_payout_ratio = false;
		boolean profit_before_game_start = false;
		boolean accounting_period_length = false;

		LinkedList<CompanyMessage> messages = new LinkedList<CompanyMessage>();
		Company company = new Company();
		for (Node child = companyNode.getFirstChild(); child != null; child = child.getNextSibling()) {
			String nodeName = child.getNodeName();
			String stringNodeValue = child.getTextContent();
			if (nodeName.equals("name")) {
				if (name)
					return null;
				name = true;
				company.setName(stringNodeValue);
			} else if (nodeName.equals("company_type")) {
				if (company_type)
					return null;
				company_type = true;
				company.setCompanyType(stringNodeValue);
			} else if (nodeName.equals("description")) {
				if (description)
					return null;
				description = true;
				company.setDescription(stringNodeValue);
			} else if (nodeName.equals("teacher_share_part")) {
				if (teacher_share_part)
					return null;
				teacher_share_part = true;
				company.setTeacherSharePart(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("total_share_number")) {
				if (total_share_number)
					return null;
				total_share_number = true;
				company.setTotalSharesAmount(Integer.parseInt(stringNodeValue));
			} else if (nodeName.equals("share_market_value")) {
				if (share_market_value)
					return null;
				share_market_value = true;
				company.setShareMarketValue(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("dividend_payout_ratio")) {
				if (dividend_payout_ratio)
					return null;
				dividend_payout_ratio = true;
				company.setDividendPayoutRatio(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("expected_profit")) {
				if (expected_profit)
					return null;
				expected_profit = true;
				company.setExpectedProfit(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("profit_before_game_start")) {
				if (profit_before_game_start)
					return null;
				profit_before_game_start = true;
				company.setProfitBeforeGameStart(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("accounting_period_length")) {
				if (accounting_period_length)
					return null;
				accounting_period_length = true;
				company.setAccountingPeriod(Integer.parseInt(stringNodeValue));
			} else if (nodeName.equals("news")) {
				CompanyMessage msg = parseNews(child);
				if (msg == null)
					return null;
				messages.add(msg);
			}
		}
		if (!name || !teacher_share_part || !total_share_number || !share_market_value || !expected_profit
				|| !dividend_payout_ratio || !profit_before_game_start || !accounting_period_length)
			return null;
		company.setId(companyId++);
		for (CompanyMessage msg : messages){
			msg.setCompanyId(company.getId());
		}
		company.setAllMessages(messages);
		company.setProfitForPreviousPeriod(company.getProfitBeforeGameStart());
		return company;
	}

	private CompanyMessage parseNews(Node newsNode) {
		boolean id = false;
		boolean type = false;
		boolean title = false;
		boolean text = false;
		boolean k_mod = false;
		boolean c_mod = false;
		CompanyMessage msg = new CompanyMessage();
		for (Node child = newsNode.getFirstChild(); child != null; child = child.getNextSibling()) {
			String nodeName = child.getNodeName();
			String stringNodeValue = child.getTextContent();
			if (nodeName.equals("id")) {
				if (id)
					return null;
				id = true;
				msg.setId(Long.parseLong(stringNodeValue));
			} else if (nodeName.equals("type")) {
				if (type)
					return null;
				type = true;
				if (stringNodeValue.equals("Rumor"))
					msg.setType(CompanyMessageType.RUMOR);
				else if (stringNodeValue.equals("OfficialInfo"))
					msg.setType(CompanyMessageType.OFFICIAL);
				else
					return null;
			} else if (nodeName.equals("title")) {
				if (title)
					return null;
				title = true;
				msg.setTitle(stringNodeValue);
			} else if (nodeName.equals("text")) {
				if (text)
					return null;
				text = true;
				msg.setMessage(stringNodeValue);
			} else if (nodeName.equals("k_mod")) {
				if (k_mod)
					return null;
				k_mod = true;
				msg.setCoefficientK(Double.parseDouble(stringNodeValue));
			} else if (nodeName.equals("c_mod")) {
				if (c_mod)
					return null;
				c_mod = true;
				msg.setCoefficientC(Double.parseDouble(stringNodeValue));
			}
		}
		if (!id || !type || !text || !k_mod || !c_mod)
			return null;
		return msg;
	}

	//TODO implement
	@Override
	public void storeScenario(IUScenario scenario, File file) throws Exception {
		throw new UnsupportedOperationException("storeScenario");

	}

}
