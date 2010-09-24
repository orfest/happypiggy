namespace HappyPiggyStudent.Model
{
    public class GameDocument
    {
        protected static GameDocument instance = null;

        public Player current_player;
        public StockExchange stock;
        public int turn_number = 1;
        //public ICommunicationUnit communication_unit;

        protected GameDocument(Player current_player, StockExchange stock)
        {
            this.current_player = current_player;
            this.stock = stock;
        }
        protected GameDocument()
        {}

        public static void Init(Player current_player, StockExchange current_stock)
        {
            instance = new GameDocument(current_player, current_stock);
        }

        public static GameDocument getInstance()
        {
            if (instance == null)
                instance = new GameDocument();
            return instance;
        }
    }
}
