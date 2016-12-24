# Dexcom CLARITY Extractor

This is a Java tool for extracting, parsing, and analyzing Dexcom CLARITY blood sugar log data.

## Pre-Requisites

You must have a Dexcom CLARITY account set up in order to be able to pull and analyze logs. Prior to using this tool to pull logs, you must log in to the Dexcom CLARITY web portal at least once in order to capture the following required configuration parameters:
- subjectId: this is your Dexcom CLARITY ID
- analysisSession: this is a session ID required for browsing CLARITY data
- accessToken: an access token required to authenticate with Dexcom CLARITY

## Obtaining Required Configs

Log into Dexcom CLARITY at [https://clarity.dexcom.com/](https://clarity.dexcom.com/). Once logged in, open up any page which displays Dexcom data, and in your browser debugger, look for the following HTTP request:

`POST https://clarity.dexcom.com/api/subject/:subjectID/analysis_session/:analysisSession/data`

Grab the `:subjectId` and `:analysisSession` from out of the URL, and in the response headers, grab the `Access-Token` value (should be a very long string; 620 in the case of my current token as of time of writing).

## AppConfig.properties

Once you have the three required configs just described, create your `AppConfig.properties` file and place it in the project root. This will be your configuration file. The following properties are supported (required where indicated):

```
accessToken=...     # required
subjectId=...       # required
analysisSession=... # required
startDate           # optional; start of date range to pull (defaults to 1 year prior to today's date)
endDate             # optional; end of date range to pull   (defaults to today's date)
extractFolder       # export directory where your logs will be saved
numThreads          # not currently implemented
```

Once that's done, you should be able to simply compile and run the program.

## Additional Features

TBD