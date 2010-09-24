namespace HappyPiggyStudent
{
    public struct Pair<U, V>
    {
        public U first;
        public V second;
    }
    public class CommonConst
    {
        public const double start_cash = 200d;
        public const string scenario_filename = "prototype.ssml";
        public const int player_number = 5;
        public const int turn_length = 60 * 7;
        public const int news_base_time = 20;
        public const int news_time = 40;
        public const int tr_base_time = 3;
        public const int tr_time = 10;
        public const int comp_buy_base_time = 10;
        public const int comp_buy_time = 15;
        public const int rem_time_base = 30;
        public const int rem_time = 10;
    }
}