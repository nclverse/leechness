# Leechness

A Clojure library to leech NESS: http://ness.ncl.ac.uk/

![LeechNESS Icon](/resources/leechness.svg "LeechNESS")

# Usage

Call leechness.core/ness with id and password to get a response and
cookie. The cookie you get back is a BasicCookieStore which can be
used for later communication with the server for further scraping.


