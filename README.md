# Android -> Google Home Notification Broadcaster

##### My use case is simple:
I've been working from home for the past 3 weeks now, and I often miss meetings and important messages from my boss that require immediate attention because I'm not always at my desk (of course, we're all "working" from home aren't we :)). 

##### And my solution is fairly straightforward
I have 3 Google Homes, one in each room and one in my bathroom.
This is a simple app that reads notifications from my phone and broadcasts them to Google Home. 
It really pissed me off when I saw Google Home doesn't have an open local API to access it. 

##### I want this!

It's fairly easy to setup. 
This requires setting up [Assistant Relay](https://github.com/greghesp/assistant-relay) for your Google Home. 

I've setup Assistant Relay to run on a Raspberry Pi that's on 24x7.
Because I only wanted this to work when I was at home, I didn't setup ngrok on my pi, but that's something you could do if you'd like to broadcast things from your phone when you're away.

##### Future

This got me thinking. Google's missing out on a huge developer market by not allowing a way for developers to access their Google Home. Yes, Actions on Google Assistant exist, but it's sluggish and feels like building apps with MIT App Inventor. Kids stuff that barely lets you scratch the surface.

But here's the fun bit. **Google Home are pull only**. It never does anything *smart* in the sense of actually informing you things at the right time. This opens the way for a lot of push based ability to my Google Home.