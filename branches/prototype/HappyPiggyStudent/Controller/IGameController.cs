using System;
using HappyPiggyStudent.Model;
using System.Xml.Serialization;
using System.IO;
using System.Windows.Forms;
using HappyPiggyStudent.Properties;

namespace HappyPiggyStudent.Controller
{
    public interface IGameController
    {
        void initGameDocument(StudentDescription sd);
        void publicNewMessage(CompanyMessage cm);
        void newTransaction(Transaction tr);
        Scenario getScenario();
        void addNewOffer(BuyOffer bo);
    }

    public class PrototypeGameController : IGameController
    {
        protected Scenario sc;
        public PrototypeGameController()
        {
            try
            {
                using (Stream s = File.OpenRead(CommonConst.scenario_filename))
                {
                    XmlSerializer xs = new XmlSerializer(typeof(Scenario));
                    sc = (Scenario)xs.Deserialize(s);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, Resources.PrototypeGameController_Scenario_load_error, MessageBoxButtons.OK, MessageBoxIcon.Error);
                throw;
            }
        }

        public void initGameDocument(StudentDescription sd)
        {
            Random r = new Random();
            int i;
            PlayerBase tch = new PlayerBase(0, new StudentDescription("Имя преподавателя", 0, ""));
            StockExchange se = new StockExchange(tch, 0);
            Bank b = new Bank(0, "Банк", new StudentDescription("Студент-банк", 0, ""), 10000, 1.05, 1.02);
            se.bank_list.Add(b);
            foreach (Company c in sc.comp_list)
            {
                se.comp_list.Add(new Company(c, false));
            }
            se.updateStockIndex();
            for(i = 0; i < CommonConst.player_number; i++)
            {
                Player p = new Player(i, new StudentDescription("Игрок " + i.ToString(), 0, ""), r.NextDouble() * 200 + 50d);
                se.player_list.Add(p);
            }
            Player own = new Player(CommonConst.player_number, sd, r.NextDouble() * 200 + 50);
            se.player_list.Add(own);
            
            foreach (Company c in se.comp_list)
            {
                int ost = c.total_share_number;
                for(i = 0; i < CommonConst.player_number; i++)
                {
                    int d = r.Next((int)(ost * 0.75d + 0.5d)) ;
                    if (d == 0)
                        continue;
                    se.player_list[i].share_list.Add(new Share(c, d, se.player_list[i]));
                    ost -= d;
                }
                own.share_list.Add(new Share(c, ost, own));
            }
            GameDocument.Init(own, se);
        }


        public void publicNewMessage(CompanyMessage cm)
        {
            cm.company.expected_profit = cm.company.expected_profit * cm.k_mod + cm.c_mod;
        }


        public void newTransaction(Transaction tr)
        {
            tr.share.company.share_market_value = tr.value / tr.share.num;
            GameDocument.getInstance().stock.updateStockIndex();
        }


        public Scenario getScenario()
        {
            return sc;
        }


        public void addNewOffer(BuyOffer bo)
        {
            GameDocument.getInstance().stock.current_offers.Add(bo);
        }
    }
}
