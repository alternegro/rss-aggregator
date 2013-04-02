package net.doxxx.rssaggregator

import akka.actor.{ActorLogging, Actor}
import akka.event.Logging
import akka.pattern._
import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import java.io.InputStreamReader
import java.net.URL
import scala.concurrent._

class FeedFetcher extends Actor with ActorLogging {
  import FeedFetcher._
  import context.dispatcher

  def receive = {
    case FetchFeed(url) => {
      log.debug("Fetching feed {}", url)
      future {
        Result(new SyndFeedInput().build(new InputStreamReader(new URL(url).openStream())))
      } pipeTo sender
    }
  }
}

object FeedFetcher {
  case class FetchFeed(url: String)
  case class Result(feed: SyndFeed)
}
