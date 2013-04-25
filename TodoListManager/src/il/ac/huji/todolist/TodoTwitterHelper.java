package il.ac.huji.todolist;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TodoTwitterHelper {
	private Twitter twitter;
	
	public TodoTwitterHelper() {
		//Though its a better practice to store strings in res.strings, i prefer to hide them here...
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("CypWcbVPcYaUeGnrKS38Yg")
		  .setOAuthConsumerSecret("mO9uYsfWADVJxLqW0iry0Ob6SATgHOv0b2102SM0a8")
		  .setOAuthAccessToken("1370435652-LnarpAevE2CavSB0mN5B6139V8s5HaezRZob62l")
		  .setOAuthAccessTokenSecret("fW64AlXtVdLI0YD3AraPDrwjmcXORp1jq0BE6fXrzTc");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	public List<Status> getTodoTweets(Context ctx) throws TwitterException {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
		String hash = pref.getString(ctx.getString(R.string.TweeterSearchPref), ctx.getString(R.string.DefaultTweeterSearchVal));
		Query query = new Query("#" + hash);
		query.setCount(100);
	    QueryResult result = twitter.search(query);
	    return result.getTweets();
	}
}
