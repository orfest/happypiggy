using System;
using System.Collections.Generic;
using System.Windows.Forms;
using HappyPiggyStudent.Controller;
using HappyPiggyStudent.Properties;

namespace HappyPiggyStudent.UI
{
    public partial class LoginWindow : Form
    {
        protected IServerFinder sfinder;
        public string username;
        public int selected_server;

        public LoginWindow(IServerFinder sfinder)
        {
            this.sfinder = sfinder;
            InitializeComponent();
            List<string> servers = sfinder.enumerateText();
            foreach(string s in servers)
            {
                lstServerFromList.Items.Add(s);
            }
            lstServerFromList.SelectedIndex = 0;
        }

        private void cmdBeginGame_Click(object sender, EventArgs e)
        {
            if (txtName.Text == string.Empty)
            {
                MessageBox.Show(this, Resources.LoginWindow_ShouldEnterUserName, Resources.LoginWindow_Error, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            username = txtName.Text;
            if (lstServerFromList.SelectedIndices.Count != 1)
            {
                MessageBox.Show(this, Resources.LoginWindow_OnlyOneServerError, Resources.LoginWindow_Error, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            selected_server = lstServerFromList.SelectedIndex;
            Close();
        }


    }
}
