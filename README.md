This is a test project on a pattern for receiving events in such way that they are not lost on rotation or when the app is minimized, 
after resuming the app the event is received.
However if a rotation happens again it will not be consumed again since it's not a state that we're saving
