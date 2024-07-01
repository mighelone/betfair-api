package com.mvasce

import com.betfair.esa.client.Client
import com.betfair.esa.client.protocol.ChangeMessage
import com.betfair.esa.client.protocol.ChangeMessageHandler
import com.betfair.esa.swagger.model.MarketChange
import com.betfair.esa.swagger.model.OrderMarketChange
import com.betfair.esa.swagger.model.StatusMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MyHandler : ChangeMessageHandler {
    override fun onOrderChange(change: ChangeMessage<OrderMarketChange>) {
        LOG.info("Order change")
    }

    override fun onMarketChange(change: ChangeMessage<MarketChange>) {
        LOG.info("Market change " + change.changeType)
        for (item in change.items) {
            LOG.info("Market id " + item.id)
        }
    }

    override fun onErrorStatusNotification(message: StatusMessage) {
        LOG.info("Error status")
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(Client::class.java)
    }
}
