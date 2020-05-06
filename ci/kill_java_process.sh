KEMENUPID=$(~/jdk/bin/jps -l | grep kemenu-backend-.*.jar | awk '{print $1}')
if [ -n "$KEMENUPID" ]; then echo "Killing app with PID $KEMENUPID" && kill -9 $KEMENUPID; else echo 'App not running'; fi