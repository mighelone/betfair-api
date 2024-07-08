package com.mvasce.betfair.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record Orderbook(
        List<OrderbookLevel> backPrices,
        List<OrderbookLevel> layPrices,
        OrderbookMetadata metadata
) {

    public Orderbook update(List<List<Double>> backPrices, List<List<Double>> layPrices, OrderbookMetadata metadata) {
        return new Orderbook(
//                this.marketId,
//                this.runnerId,
//                this.handicap,
                updatePrices(this.backPrices, backPrices),
                updatePrices(this.layPrices, layPrices),
                metadata
                );
    }



    static protected List<OrderbookLevel> updatePrices(List<OrderbookLevel> oldPrices, List<List<Double>> newPrices) {
        List<OrderbookLevel> updatedPrices = new ArrayList<>(oldPrices);
        for (List<Double> price : newPrices) {
            updatedPrices.set(
                    price.get(0).intValue(),
                    new OrderbookLevel(price.get(1), price.get(2))
            );
        }
        return updatedPrices;
    }


    static public Orderbook empty() {
        return new Orderbook(
//                null,
//                null,
//                null,
                getEmptyOrderbook(),
                getEmptyOrderbook(),
                new OrderbookMetadata(0L, 0L, null, null)
        );
    }

    static protected List<OrderbookLevel> getEmptyOrderbook() {
        List<OrderbookLevel> prices = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            prices.add(null);
        }
        return prices;
    }
}
