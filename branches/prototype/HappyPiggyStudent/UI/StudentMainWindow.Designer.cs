namespace HappyPiggyStudent.UI
{
    partial class StudentMainWindow
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea1 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Series series1 = new System.Windows.Forms.DataVisualization.Charting.Series();
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea2 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend1 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            System.Windows.Forms.DataVisualization.Charting.Series series2 = new System.Windows.Forms.DataVisualization.Charting.Series();
            this.tlpLayout = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.cmdShowChat = new System.Windows.Forms.Button();
            this.cmdShowStats = new System.Windows.Forms.Button();
            this.cmdShowFinances = new System.Windows.Forms.Button();
            this.chartStockIndex = new System.Windows.Forms.DataVisualization.Charting.Chart();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.lblMoney = new System.Windows.Forms.Label();
            this.lblTime = new System.Windows.Forms.Label();
            this.lblTurnNumber = new System.Windows.Forms.Label();
            this.cmdShowOverview = new System.Windows.Forms.Button();
            this.cmdShowNews = new System.Windows.Forms.Button();
            this.tableLayoutPanel3 = new System.Windows.Forms.TableLayoutPanel();
            this.chartShareValue = new System.Windows.Forms.DataVisualization.Charting.Chart();
            this.chkListBoxShowCompanies = new System.Windows.Forms.CheckedListBox();
            this.lstNews = new System.Windows.Forms.ListView();
            this.chType = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.chTitle = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.tabctrlBuySell = new System.Windows.Forms.TabControl();
            this.tabBuy = new System.Windows.Forms.TabPage();
            this.tableLayoutPanel5 = new System.Windows.Forms.TableLayoutPanel();
            this.lstMyOffers = new System.Windows.Forms.ListView();
            this.columnHeader4 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader5 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader6 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.label2 = new System.Windows.Forms.Label();
            this.tableLayoutPanel6 = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel7 = new System.Windows.Forms.TableLayoutPanel();
            this.lblCompListAll = new System.Windows.Forms.Label();
            this.lstCompList = new System.Windows.Forms.ListBox();
            this.panel2 = new System.Windows.Forms.Panel();
            this.cmdSell = new System.Windows.Forms.Button();
            this.txtSellOverallValue = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.txtSellShareValue = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.txtSellNum = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.txtSellMarketValue = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.txtSellOwnedShareNum = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.txtSellTotalShareNum = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.txtSellType = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtSellCompname = new System.Windows.Forms.TextBox();
            this.lblCName = new System.Windows.Forms.Label();
            this.tabSell = new System.Windows.Forms.TabPage();
            this.tableLayoutPanel4 = new System.Windows.Forms.TableLayoutPanel();
            this.lstOffers = new System.Windows.Forms.ListView();
            this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.panel1 = new System.Windows.Forms.Panel();
            this.cmdBuy = new System.Windows.Forms.Button();
            this.txtOffer = new System.Windows.Forms.TextBox();
            this.lblOffer = new System.Windows.Forms.Label();
            this.txtMyShNum = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.txtRCost = new System.Windows.Forms.TextBox();
            this.lblRcost = new System.Windows.Forms.Label();
            this.txtShNum = new System.Windows.Forms.TextBox();
            this.lblShNum = new System.Windows.Forms.Label();
            this.txtType = new System.Windows.Forms.TextBox();
            this.lblType = new System.Windows.Forms.Label();
            this.txtCompName = new System.Windows.Forms.TextBox();
            this.lblName = new System.Windows.Forms.Label();
            this.timer_Time = new System.Windows.Forms.Timer(this.components);
            this.timer_chartUpdate = new System.Windows.Forms.Timer(this.components);
            this.timer_news = new System.Windows.Forms.Timer(this.components);
            this.timer_trans = new System.Windows.Forms.Timer(this.components);
            this.timer_comp_buy = new System.Windows.Forms.Timer(this.components);
            this.timer_rem = new System.Windows.Forms.Timer(this.components);
            this.tlpLayout.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.chartStockIndex)).BeginInit();
            this.tableLayoutPanel2.SuspendLayout();
            this.tableLayoutPanel3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.chartShareValue)).BeginInit();
            this.tabctrlBuySell.SuspendLayout();
            this.tabBuy.SuspendLayout();
            this.tableLayoutPanel5.SuspendLayout();
            this.tableLayoutPanel6.SuspendLayout();
            this.tableLayoutPanel7.SuspendLayout();
            this.panel2.SuspendLayout();
            this.tabSell.SuspendLayout();
            this.tableLayoutPanel4.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tlpLayout
            // 
            this.tlpLayout.ColumnCount = 1;
            this.tlpLayout.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tlpLayout.Controls.Add(this.tableLayoutPanel1, 0, 0);
            this.tlpLayout.Controls.Add(this.tableLayoutPanel3, 0, 1);
            this.tlpLayout.Controls.Add(this.lstNews, 0, 3);
            this.tlpLayout.Controls.Add(this.tabctrlBuySell, 0, 2);
            this.tlpLayout.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tlpLayout.Location = new System.Drawing.Point(0, 0);
            this.tlpLayout.Name = "tlpLayout";
            this.tlpLayout.RowCount = 4;
            this.tlpLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 120F));
            this.tlpLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 170F));
            this.tlpLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 75F));
            this.tlpLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tlpLayout.Size = new System.Drawing.Size(894, 617);
            this.tlpLayout.TabIndex = 0;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 7;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 123F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 140F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 127F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 120F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 116F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 119F));
            this.tableLayoutPanel1.Controls.Add(this.cmdShowChat, 6, 0);
            this.tableLayoutPanel1.Controls.Add(this.cmdShowStats, 5, 0);
            this.tableLayoutPanel1.Controls.Add(this.cmdShowFinances, 4, 0);
            this.tableLayoutPanel1.Controls.Add(this.chartStockIndex, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel2, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.cmdShowOverview, 2, 0);
            this.tableLayoutPanel1.Controls.Add(this.cmdShowNews, 3, 0);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(3, 3);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 1;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(888, 114);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // cmdShowChat
            // 
            this.cmdShowChat.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmdShowChat.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cmdShowChat.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.cmdShowChat.Image = global::HappyPiggyStudent.Properties.Resources.Chat;
            this.cmdShowChat.ImageAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.cmdShowChat.Location = new System.Drawing.Point(772, 3);
            this.cmdShowChat.Name = "cmdShowChat";
            this.cmdShowChat.Size = new System.Drawing.Size(113, 108);
            this.cmdShowChat.TabIndex = 6;
            this.cmdShowChat.Text = "Чат";
            this.cmdShowChat.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.cmdShowChat.UseVisualStyleBackColor = true;
            // 
            // cmdShowStats
            // 
            this.cmdShowStats.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmdShowStats.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cmdShowStats.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.cmdShowStats.Image = global::HappyPiggyStudent.Properties.Resources.Stats_small;
            this.cmdShowStats.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.cmdShowStats.Location = new System.Drawing.Point(656, 3);
            this.cmdShowStats.Name = "cmdShowStats";
            this.cmdShowStats.Size = new System.Drawing.Size(110, 108);
            this.cmdShowStats.TabIndex = 5;
            this.cmdShowStats.Text = "Статистика";
            this.cmdShowStats.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.cmdShowStats.UseVisualStyleBackColor = true;
            // 
            // cmdShowFinances
            // 
            this.cmdShowFinances.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmdShowFinances.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cmdShowFinances.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.cmdShowFinances.Image = global::HappyPiggyStudent.Properties.Resources.MoneySection_small;
            this.cmdShowFinances.ImageAlign = System.Drawing.ContentAlignment.BottomRight;
            this.cmdShowFinances.Location = new System.Drawing.Point(536, 3);
            this.cmdShowFinances.Name = "cmdShowFinances";
            this.cmdShowFinances.Size = new System.Drawing.Size(114, 108);
            this.cmdShowFinances.TabIndex = 4;
            this.cmdShowFinances.Text = "Мои финансы";
            this.cmdShowFinances.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.cmdShowFinances.UseVisualStyleBackColor = true;
            // 
            // chartStockIndex
            // 
            this.chartStockIndex.BackColor = System.Drawing.SystemColors.Control;
            this.chartStockIndex.BackSecondaryColor = System.Drawing.SystemColors.Control;
            chartArea1.AxisX.LabelStyle.Enabled = false;
            chartArea1.AxisX.MajorGrid.Enabled = false;
            chartArea1.AxisX.TitleFont = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            chartArea1.AxisY.IsLabelAutoFit = false;
            chartArea1.AxisY.LabelStyle.Font = new System.Drawing.Font("Microsoft Sans Serif", 6F);
            chartArea1.AxisY.MajorGrid.Enabled = false;
            chartArea1.Name = "ChartArea1";
            this.chartStockIndex.ChartAreas.Add(chartArea1);
            this.chartStockIndex.Dock = System.Windows.Forms.DockStyle.Fill;
            this.chartStockIndex.Location = new System.Drawing.Point(3, 3);
            this.chartStockIndex.Name = "chartStockIndex";
            series1.BorderWidth = 5;
            series1.ChartArea = "ChartArea1";
            series1.ChartType = System.Windows.Forms.DataVisualization.Charting.SeriesChartType.Line;
            series1.IsValueShownAsLabel = true;
            series1.IsVisibleInLegend = false;
            series1.LabelToolTip = "#VAL{#.#}";
            series1.MarkerStyle = System.Windows.Forms.DataVisualization.Charting.MarkerStyle.Diamond;
            series1.Name = "Индекс";
            series1.YValuesPerPoint = 4;
            this.chartStockIndex.Series.Add(series1);
            this.chartStockIndex.Size = new System.Drawing.Size(137, 108);
            this.chartStockIndex.TabIndex = 0;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 1;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Controls.Add(this.lblMoney, 0, 2);
            this.tableLayoutPanel2.Controls.Add(this.lblTime, 0, 0);
            this.tableLayoutPanel2.Controls.Add(this.lblTurnNumber, 0, 1);
            this.tableLayoutPanel2.Location = new System.Drawing.Point(146, 3);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 3;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 45F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 55F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 34F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(117, 100);
            this.tableLayoutPanel2.TabIndex = 1;
            // 
            // lblMoney
            // 
            this.lblMoney.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblMoney.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.lblMoney.ForeColor = System.Drawing.Color.Red;
            this.lblMoney.Location = new System.Drawing.Point(3, 65);
            this.lblMoney.Name = "lblMoney";
            this.lblMoney.Size = new System.Drawing.Size(111, 35);
            this.lblMoney.TabIndex = 2;
            this.lblMoney.Text = "88 руб.";
            this.lblMoney.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblTime
            // 
            this.lblTime.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblTime.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.lblTime.ForeColor = System.Drawing.Color.Maroon;
            this.lblTime.Location = new System.Drawing.Point(3, 0);
            this.lblTime.Name = "lblTime";
            this.lblTime.Size = new System.Drawing.Size(111, 29);
            this.lblTime.TabIndex = 0;
            this.lblTime.Text = "4:44";
            this.lblTime.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblTurnNumber
            // 
            this.lblTurnNumber.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblTurnNumber.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.lblTurnNumber.Location = new System.Drawing.Point(3, 29);
            this.lblTurnNumber.Name = "lblTurnNumber";
            this.lblTurnNumber.Size = new System.Drawing.Size(111, 36);
            this.lblTurnNumber.TabIndex = 1;
            this.lblTurnNumber.Text = "Ход 3";
            this.lblTurnNumber.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // cmdShowOverview
            // 
            this.cmdShowOverview.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmdShowOverview.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cmdShowOverview.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.cmdShowOverview.Image = global::HappyPiggyStudent.Properties.Resources.piggy_tr;
            this.cmdShowOverview.ImageAlign = System.Drawing.ContentAlignment.BottomRight;
            this.cmdShowOverview.Location = new System.Drawing.Point(269, 3);
            this.cmdShowOverview.Name = "cmdShowOverview";
            this.cmdShowOverview.Size = new System.Drawing.Size(134, 108);
            this.cmdShowOverview.TabIndex = 2;
            this.cmdShowOverview.Text = "Обзор игры";
            this.cmdShowOverview.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.cmdShowOverview.UseVisualStyleBackColor = true;
            // 
            // cmdShowNews
            // 
            this.cmdShowNews.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmdShowNews.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cmdShowNews.Image = global::HappyPiggyStudent.Properties.Resources.news_small;
            this.cmdShowNews.Location = new System.Drawing.Point(409, 3);
            this.cmdShowNews.Name = "cmdShowNews";
            this.cmdShowNews.Size = new System.Drawing.Size(121, 108);
            this.cmdShowNews.TabIndex = 3;
            this.cmdShowNews.UseVisualStyleBackColor = true;
            this.cmdShowNews.Click += new System.EventHandler(this.cmdShowNews_Click);
            // 
            // tableLayoutPanel3
            // 
            this.tableLayoutPanel3.ColumnCount = 2;
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 71.05856F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 28.94144F));
            this.tableLayoutPanel3.Controls.Add(this.chartShareValue, 0, 0);
            this.tableLayoutPanel3.Controls.Add(this.chkListBoxShowCompanies, 1, 0);
            this.tableLayoutPanel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel3.Location = new System.Drawing.Point(3, 123);
            this.tableLayoutPanel3.Name = "tableLayoutPanel3";
            this.tableLayoutPanel3.RowCount = 1;
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.Size = new System.Drawing.Size(888, 164);
            this.tableLayoutPanel3.TabIndex = 1;
            // 
            // chartShareValue
            // 
            this.chartShareValue.BackColor = System.Drawing.SystemColors.Control;
            this.chartShareValue.BackSecondaryColor = System.Drawing.SystemColors.Control;
            chartArea2.AxisX.LabelStyle.Enabled = false;
            chartArea2.AxisX.MajorGrid.Enabled = false;
            chartArea2.AxisX.TitleFont = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            chartArea2.AxisY.IsLabelAutoFit = false;
            chartArea2.AxisY.LabelStyle.Font = new System.Drawing.Font("Microsoft Sans Serif", 6F);
            chartArea2.AxisY.MajorGrid.Enabled = false;
            chartArea2.Name = "ChartArea1";
            this.chartShareValue.ChartAreas.Add(chartArea2);
            this.chartShareValue.Dock = System.Windows.Forms.DockStyle.Fill;
            legend1.Font = new System.Drawing.Font("Microsoft Sans Serif", 7F);
            legend1.IsTextAutoFit = false;
            legend1.Name = "Legend1";
            this.chartShareValue.Legends.Add(legend1);
            this.chartShareValue.Location = new System.Drawing.Point(3, 3);
            this.chartShareValue.Name = "chartShareValue";
            this.chartShareValue.Palette = System.Windows.Forms.DataVisualization.Charting.ChartColorPalette.Bright;
            series2.BorderWidth = 5;
            series2.ChartArea = "ChartArea1";
            series2.ChartType = System.Windows.Forms.DataVisualization.Charting.SeriesChartType.Line;
            series2.IsValueShownAsLabel = true;
            series2.Legend = "Legend1";
            series2.LegendText = "Компания 1";
            series2.MarkerStyle = System.Windows.Forms.DataVisualization.Charting.MarkerStyle.Diamond;
            series2.Name = "Индекс";
            series2.YValuesPerPoint = 4;
            this.chartShareValue.Series.Add(series2);
            this.chartShareValue.Size = new System.Drawing.Size(625, 158);
            this.chartShareValue.TabIndex = 1;
            // 
            // chkListBoxShowCompanies
            // 
            this.chkListBoxShowCompanies.Dock = System.Windows.Forms.DockStyle.Fill;
            this.chkListBoxShowCompanies.FormattingEnabled = true;
            this.chkListBoxShowCompanies.Location = new System.Drawing.Point(634, 3);
            this.chkListBoxShowCompanies.Name = "chkListBoxShowCompanies";
            this.chkListBoxShowCompanies.Size = new System.Drawing.Size(251, 158);
            this.chkListBoxShowCompanies.TabIndex = 0;
            this.chkListBoxShowCompanies.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.chkListBoxShowCompanies_ItemCheck);
            // 
            // lstNews
            // 
            this.lstNews.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.chType,
            this.chTitle});
            this.lstNews.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lstNews.FullRowSelect = true;
            this.lstNews.GridLines = true;
            this.lstNews.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.lstNews.Location = new System.Drawing.Point(3, 538);
            this.lstNews.MultiSelect = false;
            this.lstNews.Name = "lstNews";
            this.lstNews.Size = new System.Drawing.Size(888, 76);
            this.lstNews.TabIndex = 2;
            this.lstNews.UseCompatibleStateImageBehavior = false;
            this.lstNews.View = System.Windows.Forms.View.Details;
            // 
            // chType
            // 
            this.chType.Text = "Тип сообщения";
            this.chType.Width = 100;
            // 
            // chTitle
            // 
            this.chTitle.Text = "Заголовок";
            this.chTitle.Width = 778;
            // 
            // tabctrlBuySell
            // 
            this.tabctrlBuySell.Controls.Add(this.tabBuy);
            this.tabctrlBuySell.Controls.Add(this.tabSell);
            this.tabctrlBuySell.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabctrlBuySell.Location = new System.Drawing.Point(3, 293);
            this.tabctrlBuySell.Name = "tabctrlBuySell";
            this.tabctrlBuySell.SelectedIndex = 0;
            this.tabctrlBuySell.Size = new System.Drawing.Size(888, 239);
            this.tabctrlBuySell.TabIndex = 3;
            // 
            // tabBuy
            // 
            this.tabBuy.Controls.Add(this.tableLayoutPanel5);
            this.tabBuy.Location = new System.Drawing.Point(4, 22);
            this.tabBuy.Name = "tabBuy";
            this.tabBuy.Padding = new System.Windows.Forms.Padding(3);
            this.tabBuy.Size = new System.Drawing.Size(880, 213);
            this.tabBuy.TabIndex = 0;
            this.tabBuy.Text = "Продать";
            this.tabBuy.UseVisualStyleBackColor = true;
            // 
            // tableLayoutPanel5
            // 
            this.tableLayoutPanel5.ColumnCount = 1;
            this.tableLayoutPanel5.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel5.Controls.Add(this.lstMyOffers, 0, 1);
            this.tableLayoutPanel5.Controls.Add(this.label2, 0, 0);
            this.tableLayoutPanel5.Controls.Add(this.tableLayoutPanel6, 0, 2);
            this.tableLayoutPanel5.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel5.Location = new System.Drawing.Point(3, 3);
            this.tableLayoutPanel5.Name = "tableLayoutPanel5";
            this.tableLayoutPanel5.RowCount = 3;
            this.tableLayoutPanel5.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel5.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayoutPanel5.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableLayoutPanel5.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel5.Size = new System.Drawing.Size(874, 207);
            this.tableLayoutPanel5.TabIndex = 0;
            // 
            // lstMyOffers
            // 
            this.lstMyOffers.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader4,
            this.columnHeader5,
            this.columnHeader6});
            this.lstMyOffers.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lstMyOffers.FullRowSelect = true;
            this.lstMyOffers.GridLines = true;
            this.lstMyOffers.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.lstMyOffers.Location = new System.Drawing.Point(3, 23);
            this.lstMyOffers.MultiSelect = false;
            this.lstMyOffers.Name = "lstMyOffers";
            this.lstMyOffers.Size = new System.Drawing.Size(868, 68);
            this.lstMyOffers.TabIndex = 4;
            this.lstMyOffers.UseCompatibleStateImageBehavior = false;
            this.lstMyOffers.View = System.Windows.Forms.View.Details;
            this.lstMyOffers.DoubleClick += new System.EventHandler(this.lstMyOffers_DoubleClick);
            // 
            // columnHeader4
            // 
            this.columnHeader4.Text = "Название компании";
            this.columnHeader4.Width = 129;
            // 
            // columnHeader5
            // 
            this.columnHeader5.Text = "Рыночная стоимость акции";
            this.columnHeader5.Width = 153;
            // 
            // columnHeader6
            // 
            this.columnHeader6.Text = "Предложение";
            this.columnHeader6.Width = 576;
            // 
            // label2
            // 
            this.label2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label2.Location = new System.Drawing.Point(3, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(868, 20);
            this.label2.TabIndex = 0;
            this.label2.Text = "Мои предложения (двойной щелчок, чтобы отменить):";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // tableLayoutPanel6
            // 
            this.tableLayoutPanel6.ColumnCount = 2;
            this.tableLayoutPanel6.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel6.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 600F));
            this.tableLayoutPanel6.Controls.Add(this.tableLayoutPanel7, 0, 0);
            this.tableLayoutPanel6.Controls.Add(this.panel2, 1, 0);
            this.tableLayoutPanel6.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel6.Location = new System.Drawing.Point(3, 97);
            this.tableLayoutPanel6.Name = "tableLayoutPanel6";
            this.tableLayoutPanel6.RowCount = 1;
            this.tableLayoutPanel6.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel6.Size = new System.Drawing.Size(868, 107);
            this.tableLayoutPanel6.TabIndex = 5;
            // 
            // tableLayoutPanel7
            // 
            this.tableLayoutPanel7.ColumnCount = 1;
            this.tableLayoutPanel7.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel7.Controls.Add(this.lblCompListAll, 0, 0);
            this.tableLayoutPanel7.Controls.Add(this.lstCompList, 0, 1);
            this.tableLayoutPanel7.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel7.Location = new System.Drawing.Point(3, 3);
            this.tableLayoutPanel7.Name = "tableLayoutPanel7";
            this.tableLayoutPanel7.RowCount = 2;
            this.tableLayoutPanel7.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel7.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel7.Size = new System.Drawing.Size(262, 101);
            this.tableLayoutPanel7.TabIndex = 0;
            // 
            // lblCompListAll
            // 
            this.lblCompListAll.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblCompListAll.Location = new System.Drawing.Point(3, 0);
            this.lblCompListAll.Name = "lblCompListAll";
            this.lblCompListAll.Size = new System.Drawing.Size(256, 20);
            this.lblCompListAll.TabIndex = 0;
            this.lblCompListAll.Text = "Список компаний:";
            this.lblCompListAll.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lstCompList
            // 
            this.lstCompList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lstCompList.FormattingEnabled = true;
            this.lstCompList.Location = new System.Drawing.Point(3, 23);
            this.lstCompList.Name = "lstCompList";
            this.lstCompList.Size = new System.Drawing.Size(256, 75);
            this.lstCompList.TabIndex = 1;
            this.lstCompList.SelectedIndexChanged += new System.EventHandler(this.lstCompList_SelectedIndexChanged);
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.cmdSell);
            this.panel2.Controls.Add(this.txtSellOverallValue);
            this.panel2.Controls.Add(this.label8);
            this.panel2.Controls.Add(this.txtSellShareValue);
            this.panel2.Controls.Add(this.label9);
            this.panel2.Controls.Add(this.txtSellNum);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.txtSellMarketValue);
            this.panel2.Controls.Add(this.label7);
            this.panel2.Controls.Add(this.txtSellOwnedShareNum);
            this.panel2.Controls.Add(this.label4);
            this.panel2.Controls.Add(this.txtSellTotalShareNum);
            this.panel2.Controls.Add(this.label5);
            this.panel2.Controls.Add(this.txtSellType);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Controls.Add(this.txtSellCompname);
            this.panel2.Controls.Add(this.lblCName);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(271, 3);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(594, 101);
            this.panel2.TabIndex = 1;
            // 
            // cmdSell
            // 
            this.cmdSell.Location = new System.Drawing.Point(298, 72);
            this.cmdSell.Name = "cmdSell";
            this.cmdSell.Size = new System.Drawing.Size(284, 23);
            this.cmdSell.TabIndex = 16;
            this.cmdSell.Text = "Выставить на продажу!";
            this.cmdSell.UseVisualStyleBackColor = true;
            this.cmdSell.Click += new System.EventHandler(this.cmdSell_Click);
            // 
            // txtSellOverallValue
            // 
            this.txtSellOverallValue.Location = new System.Drawing.Point(165, 81);
            this.txtSellOverallValue.Name = "txtSellOverallValue";
            this.txtSellOverallValue.ReadOnly = true;
            this.txtSellOverallValue.Size = new System.Drawing.Size(124, 20);
            this.txtSellOverallValue.TabIndex = 15;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(9, 87);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(136, 13);
            this.label8.TabIndex = 14;
            this.label8.Text = "Стоимость предложения:";
            // 
            // txtSellShareValue
            // 
            this.txtSellShareValue.Location = new System.Drawing.Point(165, 62);
            this.txtSellShareValue.Name = "txtSellShareValue";
            this.txtSellShareValue.Size = new System.Drawing.Size(124, 20);
            this.txtSellShareValue.TabIndex = 13;
            this.txtSellShareValue.TextChanged += new System.EventHandler(this.txtSellShareValue_TextChanged);
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(9, 65);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(115, 13);
            this.label9.TabIndex = 12;
            this.label9.Text = "Стоимость за акцию:";
            // 
            // txtSellNum
            // 
            this.txtSellNum.Location = new System.Drawing.Point(414, 43);
            this.txtSellNum.Name = "txtSellNum";
            this.txtSellNum.Size = new System.Drawing.Size(168, 20);
            this.txtSellNum.TabIndex = 11;
            this.txtSellNum.TextChanged += new System.EventHandler(this.txtSellNum_TextChanged);
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(295, 46);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(121, 13);
            this.label6.TabIndex = 10;
            this.label6.Text = "Число акций продажу:";
            // 
            // txtSellMarketValue
            // 
            this.txtSellMarketValue.Location = new System.Drawing.Point(165, 43);
            this.txtSellMarketValue.Name = "txtSellMarketValue";
            this.txtSellMarketValue.ReadOnly = true;
            this.txtSellMarketValue.Size = new System.Drawing.Size(124, 20);
            this.txtSellMarketValue.TabIndex = 9;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(9, 46);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(150, 13);
            this.label7.TabIndex = 8;
            this.label7.Text = "Рыночная стоимость акции:";
            // 
            // txtSellOwnedShareNum
            // 
            this.txtSellOwnedShareNum.Location = new System.Drawing.Point(414, 23);
            this.txtSellOwnedShareNum.Name = "txtSellOwnedShareNum";
            this.txtSellOwnedShareNum.ReadOnly = true;
            this.txtSellOwnedShareNum.Size = new System.Drawing.Size(168, 20);
            this.txtSellOwnedShareNum.TabIndex = 7;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(295, 26);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(78, 13);
            this.label4.TabIndex = 6;
            this.label4.Text = "Акций у меня:";
            // 
            // txtSellTotalShareNum
            // 
            this.txtSellTotalShareNum.Location = new System.Drawing.Point(128, 23);
            this.txtSellTotalShareNum.Name = "txtSellTotalShareNum";
            this.txtSellTotalShareNum.ReadOnly = true;
            this.txtSellTotalShareNum.Size = new System.Drawing.Size(161, 20);
            this.txtSellTotalShareNum.TabIndex = 5;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(9, 26);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(91, 13);
            this.label5.TabIndex = 4;
            this.label5.Text = "Акций на рынке:";
            // 
            // txtSellType
            // 
            this.txtSellType.Location = new System.Drawing.Point(414, 4);
            this.txtSellType.Name = "txtSellType";
            this.txtSellType.ReadOnly = true;
            this.txtSellType.Size = new System.Drawing.Size(168, 20);
            this.txtSellType.TabIndex = 3;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(295, 7);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(82, 13);
            this.label3.TabIndex = 2;
            this.label3.Text = "Тип компании:";
            // 
            // txtSellCompname
            // 
            this.txtSellCompname.Location = new System.Drawing.Point(128, 4);
            this.txtSellCompname.Name = "txtSellCompname";
            this.txtSellCompname.ReadOnly = true;
            this.txtSellCompname.Size = new System.Drawing.Size(161, 20);
            this.txtSellCompname.TabIndex = 1;
            // 
            // lblCName
            // 
            this.lblCName.AutoSize = true;
            this.lblCName.Location = new System.Drawing.Point(9, 7);
            this.lblCName.Name = "lblCName";
            this.lblCName.Size = new System.Drawing.Size(113, 13);
            this.lblCName.TabIndex = 0;
            this.lblCName.Text = "Название компании:";
            // 
            // tabSell
            // 
            this.tabSell.Controls.Add(this.tableLayoutPanel4);
            this.tabSell.Location = new System.Drawing.Point(4, 22);
            this.tabSell.Name = "tabSell";
            this.tabSell.Padding = new System.Windows.Forms.Padding(3);
            this.tabSell.Size = new System.Drawing.Size(880, 213);
            this.tabSell.TabIndex = 1;
            this.tabSell.Text = "Купить";
            this.tabSell.UseVisualStyleBackColor = true;
            // 
            // tableLayoutPanel4
            // 
            this.tableLayoutPanel4.ColumnCount = 1;
            this.tableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel4.Controls.Add(this.lstOffers, 0, 0);
            this.tableLayoutPanel4.Controls.Add(this.panel1, 0, 1);
            this.tableLayoutPanel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel4.Location = new System.Drawing.Point(3, 3);
            this.tableLayoutPanel4.Name = "tableLayoutPanel4";
            this.tableLayoutPanel4.RowCount = 2;
            this.tableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 100F));
            this.tableLayoutPanel4.Size = new System.Drawing.Size(874, 207);
            this.tableLayoutPanel4.TabIndex = 0;
            // 
            // lstOffers
            // 
            this.lstOffers.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3});
            this.lstOffers.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lstOffers.FullRowSelect = true;
            this.lstOffers.GridLines = true;
            this.lstOffers.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.lstOffers.Location = new System.Drawing.Point(3, 3);
            this.lstOffers.MultiSelect = false;
            this.lstOffers.Name = "lstOffers";
            this.lstOffers.Size = new System.Drawing.Size(868, 101);
            this.lstOffers.TabIndex = 3;
            this.lstOffers.UseCompatibleStateImageBehavior = false;
            this.lstOffers.View = System.Windows.Forms.View.Details;
            this.lstOffers.SelectedIndexChanged += new System.EventHandler(this.lstOffers_SelectedIndexChanged);
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Название компании";
            this.columnHeader1.Width = 129;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Рыночная стоимость акции";
            this.columnHeader2.Width = 153;
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Предложение";
            this.columnHeader3.Width = 576;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.cmdBuy);
            this.panel1.Controls.Add(this.txtOffer);
            this.panel1.Controls.Add(this.lblOffer);
            this.panel1.Controls.Add(this.txtMyShNum);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.txtRCost);
            this.panel1.Controls.Add(this.lblRcost);
            this.panel1.Controls.Add(this.txtShNum);
            this.panel1.Controls.Add(this.lblShNum);
            this.panel1.Controls.Add(this.txtType);
            this.panel1.Controls.Add(this.lblType);
            this.panel1.Controls.Add(this.txtCompName);
            this.panel1.Controls.Add(this.lblName);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(3, 110);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(868, 94);
            this.panel1.TabIndex = 4;
            // 
            // cmdBuy
            // 
            this.cmdBuy.Location = new System.Drawing.Point(698, 62);
            this.cmdBuy.Name = "cmdBuy";
            this.cmdBuy.Size = new System.Drawing.Size(157, 23);
            this.cmdBuy.TabIndex = 12;
            this.cmdBuy.Text = "Купить";
            this.cmdBuy.UseVisualStyleBackColor = true;
            this.cmdBuy.Click += new System.EventHandler(this.cmdBuy_Click);
            // 
            // txtOffer
            // 
            this.txtOffer.Location = new System.Drawing.Point(539, 30);
            this.txtOffer.Name = "txtOffer";
            this.txtOffer.ReadOnly = true;
            this.txtOffer.Size = new System.Drawing.Size(317, 20);
            this.txtOffer.TabIndex = 11;
            // 
            // lblOffer
            // 
            this.lblOffer.AutoSize = true;
            this.lblOffer.Location = new System.Drawing.Point(455, 33);
            this.lblOffer.Name = "lblOffer";
            this.lblOffer.Size = new System.Drawing.Size(80, 13);
            this.lblOffer.TabIndex = 10;
            this.lblOffer.Text = "Предложение:";
            // 
            // txtMyShNum
            // 
            this.txtMyShNum.Location = new System.Drawing.Point(539, 10);
            this.txtMyShNum.Name = "txtMyShNum";
            this.txtMyShNum.ReadOnly = true;
            this.txtMyShNum.Size = new System.Drawing.Size(317, 20);
            this.txtMyShNum.TabIndex = 9;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(455, 13);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(78, 13);
            this.label1.TabIndex = 8;
            this.label1.Text = "Акций у меня:";
            // 
            // txtRCost
            // 
            this.txtRCost.Location = new System.Drawing.Point(169, 70);
            this.txtRCost.Name = "txtRCost";
            this.txtRCost.ReadOnly = true;
            this.txtRCost.Size = new System.Drawing.Size(280, 20);
            this.txtRCost.TabIndex = 7;
            // 
            // lblRcost
            // 
            this.lblRcost.AutoSize = true;
            this.lblRcost.Location = new System.Drawing.Point(13, 73);
            this.lblRcost.Name = "lblRcost";
            this.lblRcost.Size = new System.Drawing.Size(150, 13);
            this.lblRcost.TabIndex = 6;
            this.lblRcost.Text = "Рыночная стоимость акции:";
            // 
            // txtShNum
            // 
            this.txtShNum.Location = new System.Drawing.Point(169, 50);
            this.txtShNum.Name = "txtShNum";
            this.txtShNum.ReadOnly = true;
            this.txtShNum.Size = new System.Drawing.Size(280, 20);
            this.txtShNum.TabIndex = 5;
            // 
            // lblShNum
            // 
            this.lblShNum.AutoSize = true;
            this.lblShNum.Location = new System.Drawing.Point(13, 51);
            this.lblShNum.Name = "lblShNum";
            this.lblShNum.Size = new System.Drawing.Size(91, 13);
            this.lblShNum.TabIndex = 4;
            this.lblShNum.Text = "Акций на рынке:";
            // 
            // txtType
            // 
            this.txtType.Location = new System.Drawing.Point(169, 30);
            this.txtType.Name = "txtType";
            this.txtType.ReadOnly = true;
            this.txtType.Size = new System.Drawing.Size(280, 20);
            this.txtType.TabIndex = 3;
            // 
            // lblType
            // 
            this.lblType.AutoSize = true;
            this.lblType.Location = new System.Drawing.Point(13, 33);
            this.lblType.Name = "lblType";
            this.lblType.Size = new System.Drawing.Size(82, 13);
            this.lblType.TabIndex = 2;
            this.lblType.Text = "Тип компании:";
            // 
            // txtCompName
            // 
            this.txtCompName.Location = new System.Drawing.Point(169, 10);
            this.txtCompName.Name = "txtCompName";
            this.txtCompName.ReadOnly = true;
            this.txtCompName.Size = new System.Drawing.Size(280, 20);
            this.txtCompName.TabIndex = 1;
            // 
            // lblName
            // 
            this.lblName.AutoSize = true;
            this.lblName.Location = new System.Drawing.Point(13, 13);
            this.lblName.Name = "lblName";
            this.lblName.Size = new System.Drawing.Size(113, 13);
            this.lblName.TabIndex = 0;
            this.lblName.Text = "Название компании:";
            // 
            // timer_Time
            // 
            this.timer_Time.Enabled = true;
            this.timer_Time.Interval = 1000;
            this.timer_Time.Tick += new System.EventHandler(this.timer_Time_Tick);
            // 
            // timer_chartUpdate
            // 
            this.timer_chartUpdate.Interval = 10000;
            this.timer_chartUpdate.Tick += new System.EventHandler(this.timer_chartUpdate_Tick);
            // 
            // timer_news
            // 
            this.timer_news.Tick += new System.EventHandler(this.timer_news_Tick);
            // 
            // timer_trans
            // 
            this.timer_trans.Tick += new System.EventHandler(this.timer_trans_Tick);
            // 
            // timer_comp_buy
            // 
            this.timer_comp_buy.Tick += new System.EventHandler(this.timer_comp_buy_Tick);
            // 
            // timer_rem
            // 
            this.timer_rem.Tick += new System.EventHandler(this.timer_rem_Tick);
            // 
            // StudentMainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(894, 617);
            this.Controls.Add(this.tlpLayout);
            this.Name = "StudentMainWindow";
            this.Text = "Happy Piggy - Прототип - Для студента";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.tlpLayout.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.chartStockIndex)).EndInit();
            this.tableLayoutPanel2.ResumeLayout(false);
            this.tableLayoutPanel3.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.chartShareValue)).EndInit();
            this.tabctrlBuySell.ResumeLayout(false);
            this.tabBuy.ResumeLayout(false);
            this.tableLayoutPanel5.ResumeLayout(false);
            this.tableLayoutPanel6.ResumeLayout(false);
            this.tableLayoutPanel7.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.tabSell.ResumeLayout(false);
            this.tableLayoutPanel4.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tlpLayout;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.DataVisualization.Charting.Chart chartStockIndex;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.Label lblMoney;
        private System.Windows.Forms.Label lblTime;
        private System.Windows.Forms.Label lblTurnNumber;
        private System.Windows.Forms.Button cmdShowOverview;
        private System.Windows.Forms.Button cmdShowNews;
        private System.Windows.Forms.Button cmdShowFinances;
        private System.Windows.Forms.Button cmdShowStats;
        private System.Windows.Forms.Button cmdShowChat;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel3;
        private System.Windows.Forms.DataVisualization.Charting.Chart chartShareValue;
        private System.Windows.Forms.CheckedListBox chkListBoxShowCompanies;
        private System.Windows.Forms.ListView lstNews;
        private System.Windows.Forms.ColumnHeader chType;
        private System.Windows.Forms.ColumnHeader chTitle;
        private System.Windows.Forms.TabControl tabctrlBuySell;
        private System.Windows.Forms.TabPage tabBuy;
        private System.Windows.Forms.TabPage tabSell;
        private System.Windows.Forms.Timer timer_Time;
        private System.Windows.Forms.Timer timer_chartUpdate;
        private System.Windows.Forms.Timer timer_news;
        private System.Windows.Forms.Timer timer_trans;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel4;
        private System.Windows.Forms.ListView lstOffers;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtType;
        private System.Windows.Forms.Label lblType;
        private System.Windows.Forms.TextBox txtCompName;
        private System.Windows.Forms.Label lblName;
        private System.Windows.Forms.TextBox txtShNum;
        private System.Windows.Forms.Label lblShNum;
        private System.Windows.Forms.TextBox txtMyShNum;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtRCost;
        private System.Windows.Forms.Label lblRcost;
        private System.Windows.Forms.Button cmdBuy;
        private System.Windows.Forms.TextBox txtOffer;
        private System.Windows.Forms.Label lblOffer;
        private System.Windows.Forms.Timer timer_comp_buy;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel5;
        private System.Windows.Forms.ListView lstMyOffers;
        private System.Windows.Forms.ColumnHeader columnHeader4;
        private System.Windows.Forms.ColumnHeader columnHeader5;
        private System.Windows.Forms.ColumnHeader columnHeader6;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel6;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel7;
        private System.Windows.Forms.Label lblCompListAll;
        private System.Windows.Forms.ListBox lstCompList;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtSellType;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtSellCompname;
        private System.Windows.Forms.Label lblCName;
        private System.Windows.Forms.Button cmdSell;
        private System.Windows.Forms.TextBox txtSellOverallValue;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox txtSellShareValue;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtSellNum;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtSellMarketValue;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox txtSellOwnedShareNum;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtSellTotalShareNum;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Timer timer_rem;
    }
}