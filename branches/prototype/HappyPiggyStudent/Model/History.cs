using System;
using System.Collections.Generic;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class History 
    {
        public List<HistoryElement> event_list;
        protected static History instance = null;

        protected History()
        {
            event_list = new List<HistoryElement>();
        }

        public static History GetInstance()
        {
            if (instance == null)
                instance = new History();
            return instance;
        }
    }
}
