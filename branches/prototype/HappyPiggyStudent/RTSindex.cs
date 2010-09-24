using System.Collections.Generic;
using HappyPiggyStudent.Model;

namespace HappyPiggyStudent
{
    /// <summary>
    /// Класс, обеспечивающий вычисление индекса биржи по чуть упрощенной версии
    /// методики, используемой на бирже РТС (http://www.rts.ru/?id=704 или
    /// http://web.archive.org/web/*/http://www.rts.ru/?id=704)
    /// </summary>
    public class RTSindex
    {
        protected StockExchange se;
        protected int N;
        protected const double S = 0.15;

        public RTSindex(StockExchange se)
        {
            this.se = se;
            N = se.comp_list.Count;
            
        }

        public void TurnBeginCalculate()
        {

        }

        /// <summary>
        /// Вычисление ограничивающего коэффициента Ci для всех компаний
        /// </summary>
        protected double[] calcCi()
        {
            double[] rez = new double[N];
            double[] MCapi = new double[N];
            double MCap_sum = 0;
            double MCapSumN;
            double[] si = new double[N];
            int M = 0;
            List<double> S_M = new List<double>();
            List<double> S_notM = new List<double>();
            int i;
            double X1;
            // Рыночная капитализация без ограничивающих коэффициентов
            for (i = 0; i < N; i++)
            {
                MCapi[i] = MCap(i);
                MCap_sum += MCapi[i];
            }
            // доли рыночных капитализаций эмитентов без ограничивающих коэффициентов 
            // количество эмитентов, для которых si > S
            MCapSumN = 0;
            for (i = 0; i < N; i++)
            {
                si[i] = MCapi[i] / MCap_sum;
                if (si[i] > S)
                {
                    M++;
                    S_M.Add(si[i]);
                }
                else
                {
                    S_notM.Add(si[i]);
                    MCapSumN += MCapi[i];
                }
            }
            // Рассчитывается вспомогательная величина X(1)
            X1 = (S*MCapSumN)/(1 - S*M);


            ///////////////////////////////////////////////////////////////////////////////////////    
            // TODO: finish
            ///////////////////////////////////////////////////////////////////////////////////////    
   
            return rez;
        }
             
             
             
             
             
        /// <summary>
        /// Рыночная капитализация i-ой компании без ограничивающих коэффициентов MCap_i
        /// </summary>
        /// 
        protected double MCap(int i) 
        {
            int teacherShareNum = 0;
            foreach(Share s in se.teacher.share_list)
            {
                if (s.company.name == se.comp_list[i].name)
                    teacherShareNum += s.num;
            }
            return se.comp_list[i].share_market_value * (se.comp_list[i].total_share_number - teacherShareNum);
        }

    }
}
