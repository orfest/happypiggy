using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct TimeInfo
    {
        public enum TimeState
        {
            Turn,
            TurnFinished
        }

        public TimeState type;
        public int turn_number;
        public DateTime start_time; // start time

        public TimeInfo(TimeState type, int turn_number, DateTime start_time)
        {
            this.type = type;
            this.turn_number = turn_number;
            this.start_time = start_time;
        }

        public TimeInfo(TimeInfo other)
        {
            type = other.type;
            turn_number = other.turn_number;
            start_time = other.start_time;
        }
    }
}
