using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace HappyPiggyStudent.UI
{
    public partial class NewsDetailed : Form
    {
        protected List<string> details;
        public NewsDetailed(List<string> time, List<string> type, List<string> title, List<string> details)
        {
            this.details = details;
            InitializeComponent();
            int n = time.Count;
            int i;
            for (i = n-1; i >=0 ; i--)
            {
                ListViewItem lvi = new ListViewItem();
                lvi.Text = time[i];
                lvi.SubItems.Add(type[i]);
                lvi.SubItems.Add(title[i]);
                lstNews.Items.Add(lvi);
            }

        }

        private void lstNews_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (lstNews.SelectedIndices.Count == 0)
                return;
            int s = lstNews.SelectedIndices[0];
            txtDetailed.Text = details[details.Count - 1 - s];
        }
    }
}
