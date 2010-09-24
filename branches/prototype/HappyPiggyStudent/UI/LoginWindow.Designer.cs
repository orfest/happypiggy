namespace HappyPiggyStudent.UI
{
    partial class LoginWindow
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
            this.lblWelcome = new System.Windows.Forms.Label();
            this.lblName = new System.Windows.Forms.Label();
            this.txtName = new System.Windows.Forms.TextBox();
            this.rbServerFromList = new System.Windows.Forms.RadioButton();
            this.lstServerFromList = new System.Windows.Forms.ListBox();
            this.imgPiggy = new System.Windows.Forms.PictureBox();
            this.rbIPmanually = new System.Windows.Forms.RadioButton();
            this.lblIP = new System.Windows.Forms.Label();
            this.txtIP = new System.Windows.Forms.TextBox();
            this.lblPort = new System.Windows.Forms.Label();
            this.txtPort = new System.Windows.Forms.TextBox();
            this.cmdBeginGame = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.imgPiggy)).BeginInit();
            this.SuspendLayout();
            // 
            // lblWelcome
            // 
            this.lblWelcome.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.lblWelcome.Location = new System.Drawing.Point(140, 13);
            this.lblWelcome.Name = "lblWelcome";
            this.lblWelcome.Size = new System.Drawing.Size(286, 110);
            this.lblWelcome.TabIndex = 0;
            this.lblWelcome.Text = "Добро пожаловать в HappyPiggy - обучающую программу, позволяющую Вам выступить в " +
                "роли брокера и инвестора на \"игрушечной\" бирже.";
            this.lblWelcome.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblName
            // 
            this.lblName.Location = new System.Drawing.Point(12, 142);
            this.lblName.Name = "lblName";
            this.lblName.Size = new System.Drawing.Size(122, 20);
            this.lblName.TabIndex = 1;
            this.lblName.Text = "Ваша фамилия и имя:";
            this.lblName.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // txtName
            // 
            this.txtName.Location = new System.Drawing.Point(140, 142);
            this.txtName.Name = "txtName";
            this.txtName.Size = new System.Drawing.Size(286, 20);
            this.txtName.TabIndex = 2;
            // 
            // rbServerFromList
            // 
            this.rbServerFromList.Checked = true;
            this.rbServerFromList.Location = new System.Drawing.Point(15, 179);
            this.rbServerFromList.Name = "rbServerFromList";
            this.rbServerFromList.Size = new System.Drawing.Size(203, 24);
            this.rbServerFromList.TabIndex = 3;
            this.rbServerFromList.TabStop = true;
            this.rbServerFromList.Text = "Я хочу выбрать сервер из списка";
            this.rbServerFromList.UseVisualStyleBackColor = true;
            // 
            // lstServerFromList
            // 
            this.lstServerFromList.FormattingEnabled = true;
            this.lstServerFromList.Location = new System.Drawing.Point(16, 210);
            this.lstServerFromList.Name = "lstServerFromList";
            this.lstServerFromList.Size = new System.Drawing.Size(410, 95);
            this.lstServerFromList.TabIndex = 4;
            // 
            // imgPiggy
            // 
            this.imgPiggy.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.imgPiggy.Image = global::HappyPiggyStudent.Properties.Resources.piggy1;
            this.imgPiggy.Location = new System.Drawing.Point(13, 11);
            this.imgPiggy.Name = "imgPiggy";
            this.imgPiggy.Size = new System.Drawing.Size(121, 121);
            this.imgPiggy.TabIndex = 5;
            this.imgPiggy.TabStop = false;
            // 
            // rbIPmanually
            // 
            this.rbIPmanually.Enabled = false;
            this.rbIPmanually.Location = new System.Drawing.Point(15, 311);
            this.rbIPmanually.Name = "rbIPmanually";
            this.rbIPmanually.Size = new System.Drawing.Size(249, 24);
            this.rbIPmanually.TabIndex = 6;
            this.rbIPmanually.Text = "Я хочу ввести данные сервера вручную";
            this.rbIPmanually.UseVisualStyleBackColor = true;
            // 
            // lblIP
            // 
            this.lblIP.Location = new System.Drawing.Point(13, 342);
            this.lblIP.Name = "lblIP";
            this.lblIP.Size = new System.Drawing.Size(34, 16);
            this.lblIP.TabIndex = 7;
            this.lblIP.Text = "IP:";
            this.lblIP.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // txtIP
            // 
            this.txtIP.Enabled = false;
            this.txtIP.Location = new System.Drawing.Point(53, 339);
            this.txtIP.Name = "txtIP";
            this.txtIP.Size = new System.Drawing.Size(146, 20);
            this.txtIP.TabIndex = 8;
            this.txtIP.Text = "10.1.1.1";
            // 
            // lblPort
            // 
            this.lblPort.Location = new System.Drawing.Point(205, 341);
            this.lblPort.Name = "lblPort";
            this.lblPort.Size = new System.Drawing.Size(59, 17);
            this.lblPort.TabIndex = 9;
            this.lblPort.Text = "Порт:";
            this.lblPort.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // txtPort
            // 
            this.txtPort.Enabled = false;
            this.txtPort.Location = new System.Drawing.Point(256, 339);
            this.txtPort.Name = "txtPort";
            this.txtPort.Size = new System.Drawing.Size(170, 20);
            this.txtPort.TabIndex = 10;
            this.txtPort.Text = "1234";
            // 
            // cmdBeginGame
            // 
            this.cmdBeginGame.Location = new System.Drawing.Point(244, 366);
            this.cmdBeginGame.Name = "cmdBeginGame";
            this.cmdBeginGame.Size = new System.Drawing.Size(182, 30);
            this.cmdBeginGame.TabIndex = 11;
            this.cmdBeginGame.Text = "Начать игру!";
            this.cmdBeginGame.UseVisualStyleBackColor = true;
            this.cmdBeginGame.Click += new System.EventHandler(this.cmdBeginGame_Click);
            // 
            // LoginWindow
            // 
            this.AcceptButton = this.cmdBeginGame;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(438, 403);
            this.Controls.Add(this.cmdBeginGame);
            this.Controls.Add(this.txtPort);
            this.Controls.Add(this.lblPort);
            this.Controls.Add(this.txtIP);
            this.Controls.Add(this.lblIP);
            this.Controls.Add(this.rbIPmanually);
            this.Controls.Add(this.imgPiggy);
            this.Controls.Add(this.lstServerFromList);
            this.Controls.Add(this.rbServerFromList);
            this.Controls.Add(this.txtName);
            this.Controls.Add(this.lblName);
            this.Controls.Add(this.lblWelcome);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "LoginWindow";
            this.Text = "Happy Piggy - Прототип - Для студента";
            ((System.ComponentModel.ISupportInitialize)(this.imgPiggy)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblWelcome;
        private System.Windows.Forms.Label lblName;
        private System.Windows.Forms.TextBox txtName;
        private System.Windows.Forms.RadioButton rbServerFromList;
        private System.Windows.Forms.ListBox lstServerFromList;
        private System.Windows.Forms.PictureBox imgPiggy;
        private System.Windows.Forms.RadioButton rbIPmanually;
        private System.Windows.Forms.Label lblIP;
        private System.Windows.Forms.TextBox txtIP;
        private System.Windows.Forms.Label lblPort;
        private System.Windows.Forms.TextBox txtPort;
        private System.Windows.Forms.Button cmdBeginGame;
    }
}

