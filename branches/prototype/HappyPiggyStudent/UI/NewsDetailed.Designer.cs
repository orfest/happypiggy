namespace HappyPiggyStudent.UI
{
    partial class NewsDetailed
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
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.lblNewsList = new System.Windows.Forms.Label();
            this.lstNews = new System.Windows.Forms.ListView();
            this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.txtDetailed = new System.Windows.Forms.TextBox();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.lblNewsList, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.lstNews, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.txtDetailed, 0, 2);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 3;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(414, 458);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // lblNewsList
            // 
            this.lblNewsList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblNewsList.Location = new System.Drawing.Point(3, 0);
            this.lblNewsList.Name = "lblNewsList";
            this.lblNewsList.Size = new System.Drawing.Size(408, 20);
            this.lblNewsList.TabIndex = 0;
            this.lblNewsList.Text = "Список новостей:";
            this.lblNewsList.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lstNews
            // 
            this.lstNews.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3});
            this.lstNews.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lstNews.FullRowSelect = true;
            this.lstNews.GridLines = true;
            this.lstNews.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.lstNews.Location = new System.Drawing.Point(3, 23);
            this.lstNews.MultiSelect = false;
            this.lstNews.Name = "lstNews";
            this.lstNews.Size = new System.Drawing.Size(408, 169);
            this.lstNews.TabIndex = 1;
            this.lstNews.UseCompatibleStateImageBehavior = false;
            this.lstNews.View = System.Windows.Forms.View.Details;
            this.lstNews.SelectedIndexChanged += new System.EventHandler(this.lstNews_SelectedIndexChanged);
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Время";
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Тип";
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Заголовок";
            this.columnHeader3.Width = 279;
            // 
            // txtDetailed
            // 
            this.txtDetailed.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtDetailed.Location = new System.Drawing.Point(3, 198);
            this.txtDetailed.Multiline = true;
            this.txtDetailed.Name = "txtDetailed";
            this.txtDetailed.ReadOnly = true;
            this.txtDetailed.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.txtDetailed.Size = new System.Drawing.Size(408, 257);
            this.txtDetailed.TabIndex = 2;
            // 
            // NewsDetailed
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(414, 458);
            this.Controls.Add(this.tableLayoutPanel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.SizableToolWindow;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "NewsDetailed";
            this.Text = "Новости";
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.Label lblNewsList;
        private System.Windows.Forms.ListView lstNews;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.TextBox txtDetailed;
    }
}