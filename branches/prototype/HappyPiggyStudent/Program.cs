using System;
using System.Windows.Forms;
using HappyPiggyStudent.Controller;
using HappyPiggyStudent.Model;
using HappyPiggyStudent.UI;

namespace HappyPiggyStudent
{
    static class Program
    {
 /*       static void genExampleScenario()
        {
            Scenario test = new Scenario { comp_list = new List<Company>() };
            Company c1 = new Company("Компания 1", "Банковские услуги", "Описание компании1", 40, 10d, 400, 0.8, 400);
            c1.news.Add(new CompanyMessage(0, CompanyMessage.MessageType.OfficialInfo, c1, "Пример описания официальной новости", 1, 10));
            c1.news.Add(new CompanyMessage(1, CompanyMessage.MessageType.Rumor, c1, "Пример описания слуха", 1, -1));
            c1.news.Add(new CompanyMessage(2, CompanyMessage.MessageType.OfficialInfo, c1, "Ещё официальная новость, отрицательная", 1, -4));
            c1.news.Add(new CompanyMessage(3, CompanyMessage.MessageType.Rumor, c1, "Ещё слух", 1, 1));
            c1.news.Add(new CompanyMessage(4, CompanyMessage.MessageType.OfficialInfo, c1, "Ещё нейтральная новость", 1, 0));
            c1.news.Add(new CompanyMessage(5, CompanyMessage.MessageType.Rumor, c1, "Положительный слух", 1.05, 1));
            Company c2 = new Company("Компания 2", "Перевозки", "Описание компании2", 90, 5d, 450, 0.2, 450);
            c2.news.Add(new CompanyMessage(6, CompanyMessage.MessageType.OfficialInfo, c2, "[2] Пример описания официальной новости", 1, 10));
            c2.news.Add(new CompanyMessage(7, CompanyMessage.MessageType.Rumor, c2, "[2] Пример описания слуха", 1, -1));
            c2.news.Add(new CompanyMessage(8, CompanyMessage.MessageType.OfficialInfo, c2, "[2] Ещё официальная новость, отрицательная", 1, -4));
            c2.news.Add(new CompanyMessage(9, CompanyMessage.MessageType.Rumor, c2, "[2] Ещё слух", 1, 1));
            c2.news.Add(new CompanyMessage(10, CompanyMessage.MessageType.OfficialInfo, c2, "[2] Ещё нейтральная новость", 1, 0));
            c2.news.Add(new CompanyMessage(11, CompanyMessage.MessageType.Rumor, c2, "[2] Положительный слух", 1.05, 1));
            test.comp_list.Add(c1);
            test.comp_list.Add(c2);
            XmlSerializer s = new XmlSerializer(typeof(Scenario));
            using (TextWriter w = new StreamWriter(@"e:\example.xml"))
            {
                s.Serialize(w, test);
            }
        }*/
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            IGameController gctrl;
            try
            {
                gctrl = new PrototypeGameController();
            }
            catch (Exception)
            {
                return;
            }
            using (LoginWindow login = new LoginWindow(new PrototypeServerFinder()))
            {
                Application.Run(login);
                gctrl.initGameDocument(new StudentDescription(login.username, 0, ""));
            }

            using(StudentMainWindow main = new StudentMainWindow(gctrl))
            {
                Application.Run(main);
            }
            return;

        }
    }
}
