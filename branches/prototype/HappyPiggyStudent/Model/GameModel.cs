using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct GameModel
    {
        public StockExchange stock_exchange;
        public TimeInfo time;

        public GameModel(StockExchange stock_exchange, TimeInfo time)
        {
            this.time = time;
            this.stock_exchange = stock_exchange;
        }

        public GameModel(GameModel other)
        {
            time = other.time;
            stock_exchange = other.stock_exchange;
        }
    }
}
