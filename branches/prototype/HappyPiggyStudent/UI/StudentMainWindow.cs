using System;
using System.Windows.Forms;
using HappyPiggyStudent.Model;
using HappyPiggyStudent.Controller;
using System.Collections.Generic;
using HappyPiggyStudent.Properties;

namespace HappyPiggyStudent.UI
{
    public partial class StudentMainWindow : Form
    {
        protected int tek_time = CommonConst.turn_length;
        protected IGameController igc;
        protected int selected_offer_num;
        protected bool offer_selected;
        protected List<string> ndate = new List<string>();
        protected List<string> ntype = new List<string>();
        protected List<string> ntitle = new List<string>();
        protected List<string> nmesg = new List<string>();
        protected List<int> own_ind = new List<int>();

        public string format_double(double d)
        {
            return string.Format("{0:0.##}", d);
        }

        public StudentMainWindow(IGameController igc)
        {
            offer_selected = false;
            selected_offer_num = 0;
            this.igc = igc;
            InitializeComponent();
            Update_timer();
            Update_money();
            Create_companies_list();
            Init_Sell_list();
            Init_charts();
            Update_news_timer();
            Update_tr_timer();
            Update_buy_timer();
            Update_rem_timer();
        }
        protected void Update_rem_timer()
        {
            Random r = new Random();
            timer_rem.Interval = (r.Next(CommonConst.rem_time) + CommonConst.rem_time_base) * 1000;
            timer_rem.Enabled = true;
        }


        protected void Init_Sell_list()
        {
            foreach (Company c in GameDocument.getInstance().stock.comp_list)
            {
                lstCompList.Items.Add(c.name);
            }
            lstCompList.SelectedIndex = 0;
            Update_sell_info();
        }

        protected void Update_sell_info()
        {
            if(lstCompList.SelectedIndices.Count == 0)
            {
                txtSellCompname.Text = "";
                txtSellMarketValue.Text = "";
                txtSellNum.Text = "";
                txtSellOwnedShareNum.Text = "";
                txtSellShareValue.Text = "";
                txtSellTotalShareNum.Text = "";
                txtSellType.Text = "";
                txtSellOverallValue.Text = "";
                cmdSell.Visible = false;
                txtSellNum.ReadOnly = true;
                txtSellShareValue.ReadOnly = true;
            }
            else
            {
                cmdSell.Visible = true;
                txtSellNum.ReadOnly = false;
                txtSellShareValue.ReadOnly = false;
                int cn = lstCompList.SelectedIndex;
                txtSellCompname.Text = GameDocument.getInstance().stock.comp_list[cn].name;
                txtSellMarketValue.Text = format_double(GameDocument.getInstance().stock.comp_list[cn].share_market_value);
                txtSellNum.Text = "";
                int my = 0;
                foreach (Share sh in GameDocument.getInstance().current_player.share_list)
                {
                    if (sh.company.name == GameDocument.getInstance().stock.comp_list[cn].name)
                    {
                        my += sh.num;
                        break;
                    }
                }
                txtSellOwnedShareNum.Text = my.ToString();
                txtSellShareValue.Text = format_double(GameDocument.getInstance().stock.comp_list[cn].share_market_value) + Resources.StudentMainWindow_Rubles;
                txtSellTotalShareNum.Text = GameDocument.getInstance().stock.comp_list[cn].total_share_number.ToString();
                txtSellType.Text = GameDocument.getInstance().stock.comp_list[cn].company_type;
                txtSellOverallValue.Text = "";
            }
        }

        protected void Update_offer_list()
        {
            own_ind.Clear();
            lstOffers.Items.Clear();
            lstMyOffers.Items.Clear();
            int i = 0;
            foreach(BuyOffer bo in GameDocument.getInstance().stock.current_offers)
            {
                ListViewItem lvi = new ListViewItem();
                lvi.Text = bo.share.company.name;
                lvi.SubItems.Add(format_double(bo.share.company.share_market_value));
                lvi.SubItems.Add(bo.share.num.ToString() + Resources.StudentMainWindow_Za + format_double(bo.value) + Resources.StudentMainWindow_Rubles);
                if (bo.player.id == GameDocument.getInstance().current_player.id)
                {
                    own_ind.Add(i);
                    lstMyOffers.Items.Add(lvi);
                    lvi.Text = Resources.StudentMainWindow_OwnOffer + lvi.Text;
                    lvi = (ListViewItem)lvi.Clone();
                }
                lstOffers.Items.Add(lvi);
                i++;
            }
            if (offer_selected)
            {
                if (selected_offer_num >= lstOffers.Items.Count)
                {
                    selected_offer_num = 0;
                    offer_selected = false;
                }
                else
                    lstOffers.Items[selected_offer_num].Selected = true;
            }
        }
        protected void Update_buy_timer()
        {
            Random r = new Random();
            timer_comp_buy.Interval = (r.Next(CommonConst.comp_buy_time) + CommonConst.comp_buy_base_time) * 1000;
            timer_comp_buy.Enabled = true;
        }
        protected void Update_tr_timer()
        {
            Random r = new Random();
            timer_trans.Interval = (r.Next(CommonConst.tr_time) + CommonConst.tr_base_time) * 1000;
            timer_trans.Enabled = true;
        }
        protected void Update_news_timer()
        {
            Random r = new Random();
            timer_news.Interval = (r.Next(CommonConst.news_time) + CommonConst.news_base_time) * 1000;
            timer_news.Enabled = true;
        }
        protected void Create_companies_list()
        {
            int i = 0;
            chartShareValue.Series.Clear();
            chkListBoxShowCompanies.Items.Clear();
            foreach (Company c in GameDocument.getInstance().stock.comp_list)
            {
                chartShareValue.Series.Add(c.name);
                chkListBoxShowCompanies.Items.Add(c.name);
                chkListBoxShowCompanies.SetItemChecked(i++, true);
            }

        }
        protected void Init_charts()
        {
            int j = 0;          
            int i;
            double ind = GameDocument.getInstance().stock.updateStockIndex();
            chartStockIndex.Series.Clear();
            chartStockIndex.Series.Add("Индекс");
            for (i = 0; i < 30; i++)
                chartStockIndex.Series[0].Points.AddXY(i, ind);
            chartStockIndex.Series[0].ChartType = System.Windows.Forms.DataVisualization.Charting.SeriesChartType.Line;
            //chartStockIndex.Series[0].LabelToolTip = "#VAL{#.#}";

            foreach (Company c in GameDocument.getInstance().stock.comp_list)
            {
                for (i = 0; i < 30; i++)
                {
                    chartShareValue.Series[j].Points.AddXY(i, c.share_market_value);
                }

               chartShareValue.Series[j].ChartType = System.Windows.Forms.DataVisualization.Charting.SeriesChartType.Line;
                j++;

            }
            chartStockIndex.ChartAreas[0].AxisY.IsStartedFromZero = false;
            chartShareValue.ChartAreas[0].AxisY.IsStartedFromZero = false;
        }

        protected void Update_timer()
        {
            if (tek_time == 0)
            {
                GameDocument.getInstance().turn_number++;
                tek_time = CommonConst.turn_length;
            }
            int min = tek_time / 60;
            int sec = tek_time % 60;
            lblTime.Text = min.ToString() + (sec < 10 ? ":0" : ":") + sec.ToString();
            lblTurnNumber.Text = Resources.StudentMainWindow_Turn + GameDocument.getInstance().turn_number.ToString();
            Update_money();
        }

        protected void Update_money()
        {
            lblMoney.Text = format_double(GameDocument.getInstance().current_player.cash) + Resources.StudentMainWindow_Rubles;
        }

        private void timer_Time_Tick(object sender, EventArgs e)
        {
            tek_time--;
            Update_timer();
        }



        private void chkListBoxShowCompanies_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            chartShareValue.Series[e.Index].Enabled = (e.NewValue == CheckState.Checked);
            chartShareValue.ChartAreas[0].RecalculateAxesScale();
        }

        protected void Update_charts()
        {
            int i;
            for (i = 0; i < 30 - 1; i++)
                chartStockIndex.Series[0].Points[i].SetValueXY(i, chartStockIndex.Series[0].Points[i + 1].YValues[0]);
            chartStockIndex.Series[0].Points[29].SetValueXY(29, GameDocument.getInstance().stock.updateStockIndex());
            int j = 0;
            foreach (Company c in GameDocument.getInstance().stock.comp_list)
            {
                for (i = 0; i < 30 - 1; i++)
                    chartShareValue.Series[j].Points[i].SetValueXY(i, chartShareValue.Series[j].Points[i + 1].YValues[0]);
                chartShareValue.Series[j].Points[29].SetValueXY(29, c.share_market_value);

                j++;

            }
            chartStockIndex.ChartAreas[0].RecalculateAxesScale();
            chartShareValue.ChartAreas[0].RecalculateAxesScale();

        }
        protected void Sell_offer_text_clear()
        {
            txtCompName.Text = "";
            txtRCost.Text = "";
            txtMyShNum.Text = "";
            txtOffer.Text = "";
            txtShNum.Text = "";
            txtType.Text = "";
            cmdBuy.Visible = false;
            selected_offer_num = 0;
            offer_selected = false;
        }

        private void timer_chartUpdate_Tick(object sender, EventArgs e)
        {
            Update_charts();
        }

        private void timer_news_Tick(object sender, EventArgs e)
        {
            Random r = new Random();
            int cnum = r.Next(igc.getScenario().comp_list.Count);
            if (igc.getScenario().comp_list[cnum].news.Count == 0)
            {
                Update_news_timer();
                return;
            }
            int nnum = r.Next(igc.getScenario().comp_list[cnum].news.Count);
            CompanyMessage cm = igc.getScenario().comp_list[cnum].news[nnum];
            cm.company = GameDocument.getInstance().stock.comp_list[cnum];
            igc.publicNewMessage(cm);
            cm.company.news.Add(cm);
            ListViewItem lvi = new ListViewItem();
            lvi.Text = (cm.type == CompanyMessage.MessageType.OfficialInfo ? "Новость" : "Слух");
            lvi.SubItems.Add(cm.title);
            lstNews.Items.Add(lvi);
            ndate.Add("[" + GameDocument.getInstance().turn_number + "] " + lblTime.Text);
            ntype.Add((cm.type == CompanyMessage.MessageType.OfficialInfo ? "Новость" : "Слух"));
            ntitle.Add(cm.title);
            nmesg.Add(cm.text);
             
            cmdShowNews.Image = Resources.news_small_new;
            Update_news_timer();
        }

        private void cmdShowNews_Click(object sender, EventArgs e)
        {
            cmdShowNews.Image = Resources.news_small;
            using (NewsDetailed nd = new NewsDetailed(ndate, ntype, ntitle, nmesg))
            {
                nd.ShowDialog(this);
            }
        }

        private void timer_trans_Tick(object sender, EventArgs e)
        {
            Random r = new Random();
            int pl_num = r.Next(GameDocument.getInstance().stock.player_list.Count - 1);
            int comp_num = r.Next(GameDocument.getInstance().stock.comp_list.Count);
            int num;
            Share t_s = null;
            foreach (Share s in GameDocument.getInstance().stock.player_list[pl_num].share_list)
            {
                if (s.company.name == GameDocument.getInstance().stock.comp_list[comp_num].name)
                {
                    t_s = s;
                    break;
                }
            }
            if (t_s == null || (num = r.Next(t_s.num)) == 0)
            {
                 Update_tr_timer();
                 Update_offer_list();
                 return;            
            }
            Share snew = new Share(t_s);
            snew.num = num;
            t_s.num -= num;

            BuyOffer bo = new BuyOffer(snew, GameDocument.getInstance().stock.player_list[pl_num], 
                snew.company.share_market_value * (0.95 + 0.1 * r.NextDouble()) * num);
            GameDocument.getInstance().stock.current_offers.Add(bo);
            Update_sell_info();
            Update_offer_list();
        }

        private void lstOffers_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (lstOffers.SelectedIndices.Count == 0)
            {
                selected_offer_num = 0;
                offer_selected = false;
                Sell_offer_text_clear();
                return;
            }
            int s = lstOffers.SelectedIndices[0];
            selected_offer_num = s;
            offer_selected = true;
            Share current_offer_sh = GameDocument.getInstance().stock.current_offers[s].share;
            BuyOffer current_offer = GameDocument.getInstance().stock.current_offers[s];
            txtCompName.Text = current_offer_sh.company.name;
            int my = 0;
            foreach (Share sh in GameDocument.getInstance().current_player.share_list)
            {
                if (sh.company.name == current_offer_sh.company.name)
                    my += sh.num;
            }
            txtMyShNum.Text = my.ToString();
            txtOffer.Text = current_offer_sh.num.ToString() + Resources.StudentMainWindow_Za + format_double(current_offer.value) + Resources.StudentMainWindow_Rubles;
            txtRCost.Text = format_double(current_offer_sh.company.share_market_value) + Resources.StudentMainWindow_Rubles;
            txtShNum.Text = current_offer_sh.company.total_share_number.ToString();
            txtType.Text = current_offer_sh.company.company_type;
            cmdBuy.Visible = true;
        }

        private void timer_comp_buy_Tick(object sender, EventArgs e)
        {
            int i;
            Random r = new Random();
            int pl_num = r.Next(GameDocument.getInstance().stock.player_list.Count - 1);
            int off_num = r.Next(GameDocument.getInstance().stock.current_offers.Count);
            if (GameDocument.getInstance().stock.current_offers.Count == 0)
            {
                Update_buy_timer();
                return;
            }
            double val = GameDocument.getInstance().stock.current_offers[off_num].value;
            if(GameDocument.getInstance().stock.player_list[pl_num].cash < val)
            {
                Update_buy_timer();
                return;
            }
            // Проверить разницу с рыночной стоимостью, принять решение о покупке
            double marketCost = GameDocument.getInstance().stock.current_offers[off_num].share.company.share_market_value;
            double cost = val / GameDocument.getInstance().stock.current_offers[off_num].share.num;
            if (marketCost < cost)
            {
                double prob = marketCost / cost;
                if (r.NextDouble() > prob)
                {
                    Update_buy_timer();
                    return;                    
                }
            }

            bool toadd = true;
            for (i = 0; i < GameDocument.getInstance().stock.player_list[pl_num].share_list.Count; i++)
            {
                if (GameDocument.getInstance().stock.player_list[pl_num].share_list[i].company.name
                    == GameDocument.getInstance().stock.current_offers[off_num].share.company.name)
                {
                    toadd = false;
                    GameDocument.getInstance().stock.player_list[pl_num].share_list[i].company.share_market_value =
                        GameDocument.getInstance().stock.current_offers[off_num].value /
                        GameDocument.getInstance().stock.current_offers[off_num].share.num;
                    GameDocument.getInstance().stock.updateStockIndex();

                    Update_charts();
                    GameDocument.getInstance().stock.player_list[pl_num].share_list[i].num += 
                        GameDocument.getInstance().stock.current_offers[off_num].share.num;
                    GameDocument.getInstance().stock.player_list[pl_num].share_list[i].owner = GameDocument.getInstance().stock.player_list[pl_num];
                    break;
                }
            }
            if (toadd)
            {
                GameDocument.getInstance().stock.player_list[pl_num].share_list.Add(GameDocument.getInstance().stock.current_offers[off_num].share);
                GameDocument.getInstance().stock.player_list[pl_num].share_list[GameDocument.getInstance().stock.player_list[pl_num].share_list.Count - 1].owner =
    GameDocument.getInstance().stock.player_list[pl_num];
                GameDocument.getInstance().stock.current_offers[off_num].share.company.share_market_value =
                    GameDocument.getInstance().stock.current_offers[off_num].value / GameDocument.getInstance().stock.current_offers[off_num].share.num;
                GameDocument.getInstance().stock.updateStockIndex();
                Update_charts();
            }
            GameDocument.getInstance().stock.current_offers[off_num].player.cash += val;
            GameDocument.getInstance().stock.player_list[pl_num].cash -= val;
            GameDocument.getInstance().stock.current_offers.RemoveAt(off_num);
            if (offer_selected && off_num == selected_offer_num)
                Sell_offer_text_clear();        
            Update_offer_list();
            Update_sell_info();
            Update_buy_timer();
        }

        private void cmdBuy_Click(object sender, EventArgs e)
        {
            int i;
            int off_num = selected_offer_num;
            if (GameDocument.getInstance().current_player.id == GameDocument.getInstance().stock.current_offers[off_num].player.id)
            {
                MessageBox.Show(Resources.StudentMainWindow_BuyOwn, Resources.StudentMainWindow_Error, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            double val = GameDocument.getInstance().stock.current_offers[off_num].value;
            if (GameDocument.getInstance().current_player.cash < val)
            {
                MessageBox.Show(Resources.StudentMainWindow_NotEnoughMoney, Resources.StudentMainWindow_Error, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            bool toadd = true;
            for (i = 0; i < GameDocument.getInstance().current_player.share_list.Count; i++)
            {
                if (GameDocument.getInstance().current_player.share_list[i].company.name
                    == GameDocument.getInstance().stock.current_offers[off_num].share.company.name)
                {
                    toadd = false;
                    GameDocument.getInstance().current_player.share_list[i].company.share_market_value =
                        GameDocument.getInstance().stock.current_offers[off_num].value /
                        GameDocument.getInstance().stock.current_offers[off_num].share.num;
                    GameDocument.getInstance().stock.updateStockIndex();

                    Update_charts();
                    GameDocument.getInstance().current_player.share_list[i].num +=
                        GameDocument.getInstance().stock.current_offers[off_num].share.num;
                    GameDocument.getInstance().current_player.share_list[i].owner = GameDocument.getInstance().current_player;
                }
            }
            if (toadd)
            {
                GameDocument.getInstance().current_player.share_list.Add(GameDocument.getInstance().stock.current_offers[off_num].share);
                GameDocument.getInstance().current_player.share_list[GameDocument.getInstance().current_player.share_list.Count - 1].owner =
                    GameDocument.getInstance().current_player;
                GameDocument.getInstance().stock.current_offers[off_num].share.company.share_market_value =
                    GameDocument.getInstance().stock.current_offers[off_num].value / GameDocument.getInstance().stock.current_offers[off_num].share.num;
                GameDocument.getInstance().stock.updateStockIndex();
                Update_charts();
            }
            GameDocument.getInstance().stock.current_offers[off_num].player.cash += val;
            GameDocument.getInstance().current_player.cash -= val;
            GameDocument.getInstance().stock.current_offers.RemoveAt(off_num);
            if (offer_selected && off_num == selected_offer_num)
                Sell_offer_text_clear();
            Update_offer_list();
            Update_sell_info();
            Update_buy_timer();
            Update_money();

        }

        private void txtSellNum_TextChanged(object sender, EventArgs e)
        {
            try
            {
                int num = int.Parse(txtSellNum.Text);
                double c = double.Parse(txtSellShareValue.Text.Split(new char[] { ' ' })[0]);
                double overall = c * num;
                txtSellOverallValue.Text = format_double(overall) + Resources.StudentMainWindow_Rubles;
            }
            catch (Exception)
            {}
        }

        private void txtSellShareValue_TextChanged(object sender, EventArgs e)
        {
            try
            {
                int num = int.Parse(txtSellNum.Text);
                double c = double.Parse(txtSellShareValue.Text.Split(new char[] { ' ' })[0]);
                double overall = c * num;
                txtSellOverallValue.Text = format_double(overall) + Resources.StudentMainWindow_Rubles;
            }
            catch (Exception)
            { }
        }

        private void lstCompList_SelectedIndexChanged(object sender, EventArgs e)
        {
            Update_sell_info();
        }

        private void cmdSell_Click(object sender, EventArgs e)
        {
            int num = 0;
            double c = 0d;
            try
            {
                num = int.Parse(txtSellNum.Text);
                c = double.Parse(txtSellShareValue.Text.Split(new char[] { ' ' })[0]);
                double overall = c * num;
                txtSellOverallValue.Text = format_double(overall) + Resources.StudentMainWindow_Rubles;
                if (c < 0 || num < 0)
                    throw new Exception();
            }
            catch (Exception)
            {
                MessageBox.Show(Resources.StudentMainWindow_WrongShareNumber, Resources.StudentMainWindow_Error, 
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            Share t_s = null;
            foreach (Share s in GameDocument.getInstance().current_player.share_list)
            {
                if (s.company.name == txtSellCompname.Text)
                {
                    t_s = s;
                    break;
                }
            }
            if (num == 0 || t_s == null)
            {
                MessageBox.Show(Resources.StudentMainWindow_ZeroShareNumberError, Resources.StudentMainWindow_Error,
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            if (num > t_s.num)
            {
                MessageBox.Show(Resources.StudentMainWindow_cmdSell_TooManySharesError, Resources.StudentMainWindow_Error, 
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            Share snew = new Share(t_s);
            snew.num = num;
            t_s.num -= num;

            BuyOffer bo = new BuyOffer(snew, GameDocument.getInstance().current_player, c*num);
            GameDocument.getInstance().stock.current_offers.Add(bo);
            Update_sell_info();
            Update_offer_list();
        }

        private void lstMyOffers_DoubleClick(object sender, EventArgs e)
        {
            int i;
            if (lstMyOffers.SelectedIndices.Count == 0)
                return;
            int num = own_ind[lstMyOffers.SelectedIndices[0]];
            Player pl = GameDocument.getInstance().stock.current_offers[num].player;
            for(i = 0; i < pl.share_list.Count; i++)
            {
                if (pl.share_list[i].company.name == GameDocument.getInstance().stock.current_offers[num].share.company.name)
                    break;
            }
            pl.share_list[i].num += GameDocument.getInstance().stock.current_offers[num].share.num;
            GameDocument.getInstance().stock.current_offers.RemoveAt(num);
            Update_sell_info();
            Update_offer_list();
        }

        private void timer_rem_Tick(object sender, EventArgs e)
        {
            int i;
            Random r = new Random();
            int num = r.Next(GameDocument.getInstance().stock.current_offers.Count);
            Player pl = GameDocument.getInstance().stock.current_offers[num].player;
            if(pl.id == GameDocument.getInstance().current_player.id)
            {
                Update_rem_timer();
                return;
            }
            for (i = 0; i < pl.share_list.Count; i++)
            {
                if (pl.share_list[i].company.name == GameDocument.getInstance().stock.current_offers[num].share.company.name)
                    break;
            }
            pl.share_list[i].num += GameDocument.getInstance().stock.current_offers[num].share.num;
            GameDocument.getInstance().stock.current_offers.RemoveAt(num);
            Update_rem_timer();
            Update_sell_info();
            Update_offer_list();
        }


    }
}
