using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Xml.Schema;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class Company
    {
        public string name;
        public string company_type;
        public string description;
        public int total_share_number;
        public double share_market_value;
        [XmlElementAttribute("news", Form = XmlSchemaForm.Unqualified, Type = typeof(CompanyMessage))]
        public List<CompanyMessage> news;
        public double expected_profit;
        public double dividend_payout_ratio;
        public double profit_before_game_start;
        [XmlIgnore]
        public List<Share> share_list;

        public Company()
        {
            news = new List<CompanyMessage>();
            share_list = new List<Share>();
        }

        public Company(string name, string company_type, string description, int total_share_number,
            double share_market_value, double expected_profit, double dividend_payout_ratio,
            double profit_before_game_start)
        {
            this.name = name;
            this.company_type = company_type;
            this.description = description;
            this.total_share_number = total_share_number;
            this.share_market_value = share_market_value;
            this.expected_profit = expected_profit;
            this.dividend_payout_ratio = dividend_payout_ratio;
            this.profit_before_game_start = profit_before_game_start;
            news = new List<CompanyMessage>();
            share_list = new List<Share>(total_share_number);
        }

        
        public Company(Company other, bool deep)
        {
            name = other.name;
            company_type = other.company_type;
            description = other.description;
            total_share_number = other.total_share_number;
            share_market_value = other.share_market_value;
            if (deep)
                news = new List<CompanyMessage>(other.news);
            else
                news = new List<CompanyMessage>();
            expected_profit = other.expected_profit;
            dividend_payout_ratio = other.dividend_payout_ratio;
            profit_before_game_start = other.profit_before_game_start;
            if (deep)
                share_list = new List<Share>(other.share_list);
            else
                share_list = new List<Share>(total_share_number);
        }
    }
}
