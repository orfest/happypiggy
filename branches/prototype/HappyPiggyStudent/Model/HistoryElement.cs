using System;
namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct HistoryElement
    {
        public enum HistoryElementType
        {
            BuyOffer, 
            Transaction,
            CompanyMessage,
            Deposit,
            Loan,
            DivVoting
        }
        public int turn_number;
        public int time;
        public HistoryElementType event_type;
        public object event_obj;

        public HistoryElement(int turn_number, int time, HistoryElementType event_type, ICloneable event_obj)
        {
            this.turn_number = turn_number;
            this.time = time;
            this.event_type = event_type;
            this.event_obj = event_obj.Clone();
        }
    }
}
