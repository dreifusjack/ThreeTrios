### This README file is for summarizing which features were and were not able to get working.
### Our original README file can be found in the docs directory. 

In this assignment we were able to successfully get all features working such that a game of ThreeTrios
can be played using our view for the red controller and the provided view for the blue controller. Both
of these views allow for interaction and the controllers work in the proper synchronous game flow. Additionally,
both controllers/views are internally tracked and ensured through our model. All game types
work between both view/controllers: human v human, human v AI, AI v human. Also, all proper notifications are sent
to both controllers (listeners) and callback to each view correctly. 

Here is a brief overview of how we adapted the provide code to get all the game features working. 
We followed the object adapter pattern to implement their model with ours as a delegate, 
that way we were able to construct an instance of the provided view. From there we implemented an 
adapter controller that adapts our model and player action type to create an instance of their model 
to creates an instance of their view. This adapted controller allows the blue team to interact with 
the provided view while still properly updating and getting notifications from our model.

An alternative approach is to adapt their view to our view interface. That would have allowed us to 
directly pass in the adapted view in our controller implementation which would have been easier and reused 
more code. The problem with that is how were previously implemented our controller. Our controller incorrectly
handles view logic within its methods. For that reason if we adapted the provided view and our controller was
built to operate on our view this would break the provided view since it was implemented in a much different way. 
For that reason we went with our approach, while it took more adapters and more code, it successfully allows 
all the features to work without changing any of our old code or the providers code. 