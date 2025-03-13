package com.mvasce.betfair.models;

import com.betfair.esa.client.protocol.ChangeType;
import com.betfair.esa.client.protocol.SegmentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderbookTest {

    @Test
    void update() {
        Orderbook orderbook = Orderbook.empty();
        orderbook = orderbook.update(
                List.of(
                        List.of(0.0, 2.3, 100.),
                        List.of(2.0, 2.5, 10.),
                        List.of(1.0, 2.4, 100.)
                ),
                List.of(
                        List.of(0.0, 2.2, 50.)
                ),
                new OrderbookMetadata(0L, 0L, ChangeType.SUB_IMAGE, SegmentType.NONE)
        ).update(
                List.of(
                        List.of(2.0, 2.5, 10.),
                        List.of(0.0, 2.3, 150.)
                ),
                List.of(),
                new OrderbookMetadata(1L, 1L, ChangeType.UPDATE, SegmentType.NONE)
        ).update(
                List.of(
                        List.of(2.0, 2.4, 10.),
                        List.of(3.0, 2.5, 10.)

                ),
                List.of(List.of(0.0, 2.25, 10.), List.of(1.0, 2.3, 0.0)),
                new OrderbookMetadata(2L, 2L, ChangeType.UPDATE, SegmentType.NONE)
        );
        final Orderbook expected = getOrderbook();

        assertEquals(expected, orderbook);
    }

    private static Orderbook getOrderbook() {
        var backs = Orderbook.getEmptyOrderbook();
        backs.set(0, new OrderbookLevel(2.3, 150.));
        backs.set(1, new OrderbookLevel(2.4, 100.));
        backs.set(2, new OrderbookLevel(2.4, 10.));
        backs.set(3, new OrderbookLevel(2.5, 10.));
        var lays = Orderbook.getEmptyOrderbook();
        lays.set(0, new OrderbookLevel(2.25, 10.));
        lays.set(1, new OrderbookLevel(2.3, 0.));

        return new Orderbook(backs, lays, new OrderbookMetadata(2L, 2L, ChangeType.UPDATE, SegmentType.NONE));
    }

    @Test
    void updatePrices() {

        List<OrderbookLevel> prices = Orderbook.getEmptyOrderbook() ;
        prices.set(0,  new OrderbookLevel(2.4, 100.0));
        prices.set(1, new OrderbookLevel(2.5, 234.));
        prices.set(2, new OrderbookLevel(2.6, 10.0));

        List<List<Double>> updates = List.of(
                List.of(0., 2.3, 50.),
                List.of(1., 2.4, 100.),
                List.of(3., 2.7, 10.)
        );

        List<OrderbookLevel> expected = Orderbook.getEmptyOrderbook();

        expected.set(0, new OrderbookLevel(2.3, 50.0));
        expected.set(1, new OrderbookLevel(2.4, 100.));
        expected.set(2, new OrderbookLevel(2.6, 10.0));
        expected.set(3, new OrderbookLevel(2.7, 10.));

        var result = Orderbook.updatePrices(prices, updates);
        assertIterableEquals(expected, result);
    }
}