package com.mvasce

import com.betfair.esa.client.Client
import com.betfair.esa.client.auth.AppKeyAndSessionProvider
import com.betfair.esa.client.auth.InvalidCredentialException
import com.betfair.esa.client.protocol.ConnectionException
import com.betfair.esa.client.protocol.StatusException
import com.betfair.esa.swagger.model.MarketDataFilter
import com.betfair.esa.swagger.model.MarketDataFilter.FieldsEnum
import com.betfair.esa.swagger.model.MarketFilter
import com.betfair.esa.swagger.model.MarketSubscriptionMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

//@Slf4j
object Main {
    private val LOG: Logger = LoggerFactory.getLogger(Client::class.java)
    @Throws(StatusException::class, InvalidCredentialException::class, ConnectionException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        LOG.info("Start")

        val sessionProvider = AppKeyAndSessionProvider(
            AppKeyAndSessionProvider.SSO_HOST_COM,
            "EfcnNLv9DoymnCjn",
            "michele.vascellari@gmail.com",
            "Durlindana66?"
        )
        val client = Client(
            "stream-api.betfair.com",  //                "stream-api-integration.betfair.com",
            443,
            sessionProvider
        )

        client.changeHandler = MyHandler()

        val marketFilter = marketFilter
        val marketDataFilter = marketDataFilter

        client.marketDataFilter = marketDataFilter
        //        client.setConflateMs(1000L);
//        client.setHeartbeatMs(2000L);
        client.changeHandler = MyHandler()
        val subscription = MarketSubscriptionMessage()
        //        subscription.setConflateMs(0L);
//        subscription.setClk();
//        subscription.setHeartbeatMs(10L);
        subscription.marketFilter = marketFilter
        client.start()
        client.marketSubscription(subscription)

        LOG.info("Client started")
    }

    private val marketFilter: MarketFilter
        get() {
            val marketFilter = MarketFilter()
            marketFilter.eventTypeIds = listOf("4")
            marketFilter.countryCodes = listOf("GB")
            marketFilter.marketTypes = listOf("MATCH_ODDS")
            return marketFilter
        }

    private val marketDataFilter: MarketDataFilter
        get() {
            val marketDataFilter = MarketDataFilter()
            marketDataFilter.ladderLevels = 10
            marketDataFilter.fields = listOf(
                FieldsEnum.EX_BEST_OFFERS,
                FieldsEnum.EX_MARKET_DEF,
                FieldsEnum.EX_ALL_OFFERS,
                FieldsEnum.EX_TRADED,
                FieldsEnum.EX_TRADED_VOL
            )
            return marketDataFilter
        }
}