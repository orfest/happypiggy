using System;
using System.Collections.Generic;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class StockExchange
    {
        public List<Player> player_list;
        public List<Company> comp_list;
        public List<Bank> bank_list;
        public List<BuyOffer> current_offers;
        public PlayerBase teacher;
        public double stock_index;

        public double updateStockIndex()
        {
            int zn = 0;
            double ch = 0d;
            foreach(Company c in comp_list)
            {
                ch += c.share_market_value * c.total_share_number;
                zn += c.total_share_number;
            }
            stock_index = ch / zn;
            return stock_index;
        }

        public StockExchange()
        {
            comp_list = new List<Company>();
            bank_list = new List<Bank>();
            current_offers = new List<BuyOffer>();
            player_list = new List<Player>();
        }

        public StockExchange(PlayerBase teacher, double stock_index)
        {
            this.teacher = teacher;
            this.stock_index = stock_index;
            comp_list = new List<Company>();
            bank_list = new List<Bank>();
            current_offers = new List<BuyOffer>();
            player_list = new List<Player>();
        }

        public StockExchange(StockExchange other, bool deep)
        {
            teacher = other.teacher;
            stock_index = other.stock_index;
            if (deep)
            {
                comp_list = new List<Company>(other.comp_list);
                bank_list = new List<Bank>(other.bank_list);
                current_offers = new List<BuyOffer>(other.current_offers);
                player_list = new List<Player>(other.player_list);
            }
            else
            {
                comp_list = new List<Company>();
                bank_list = new List<Bank>();
                current_offers = new List<BuyOffer>();
                player_list = new List<Player>();
            }
        }
    }
}
