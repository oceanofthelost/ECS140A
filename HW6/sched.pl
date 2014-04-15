/* Part 1: Simple Queries */

/* numbers of all courses */ 
c_numbers(N) :-
	course(N,_,_).

/* numbers of all programming languages courses */
c_pl(N) :-
    course(N,programming_languages,_).

/* numbers of all non-programming languages courses */
c_notpl(N) :-
    c_numbers(N),\+c_pl(N).

/* list of those teaching 60 */
c_inst60(L) :-
    course(60,_,L).

/* sorted list of those teaching 60 */
c_inst60_sorted(L) :-
    c_inst60(L), sort(L).

/*  list of those teaching 20 */
c_inst20(L) :-
    course(20,_,L).

/* sorted list of those teaching 20 */
c_inst20_sorted(L) :-
    c_inst20(L), sort(L).

/* sorted list of those teaching N */
c_inst_sorted(N,L) :-
    course(N,_,L), sort(L).

/* numbers of courses with exactly one instructor */ 
c_single_inst(N) :-
    course(N,_,[_]).

/* numbers of courses with more than one instructor */
c_multi_inst(N) :-
    c_numbers(N),\+c_single_inst(N).

/* numbers of courses for which I is the only instructor */
c_exclusive(I,N) :-
    course(N,_,[I]).

/* numbers of courses with exactly one or exactly two instructors */
c_12_inst_1or(N) :-
    course(N,_,[_]); course(N,_,[_,_]).

/* numbers of courses with exactly one or exactly two instructors */
c_12_inst_2wo(N) :-
    course(N,_,[_]).

c_12_inst_2wo(N) :-
    course(N,_,[_,_]).

/* Part 2: Basic Lists */

/* Write the predicate delete_question(Answer) */
delete_question("4th Edition: Page 145").

/* takes two lists, appends them into a new list */
sortappend(X,Y,Z) :-
    append(X,Y,Z),sort(Z).

/* Part 3: List Manipulation */

/* returns Y pairs. W is ﬁrst element, and X is the second element */
distribute(_,[],[]).

distribute(W,[H|T],Y) :-
    distribute(W,T,Z),
    append([W],[H],Temp),
    append([Temp],Z,Y).


/* Part 4: Cross Product */

/* myfor(L,U,Result)*/
myfor(L,U,Result) :-
    L =< U,
    L1 is L+1,
    myfor(L1,U,Res1),
    Result = [L | Res1].
myfor(L,U,[]) :-
    L>U.

/* list Z the cross product of myfor(1,R,X) with myfor(1,H,Y) */
crossmyfor(R,H,Z) :-
    myfor(1,R,X),
    myfor(1,H,Y),
    ( X \= [],
      Y \= [] ->
       crossdistribute(X,Y,Z)     
    ;
       Z = []
    ).

crossdistribute([H|T],X,Z) :-
    crossdistribute(T,X,Y),
    distribute(H,X,Temp),
    append(Temp,Y,Z).

crossdistribute([H],X,Z) :-
    distribute(H,X,Z).

/* Part 5: Meeting Scheduling */

/* Part 5a : getallmeetings */

/*
* Base case. if there is an empty list we return an ampty list
*/
getallmeetings([],[]).

/*
* we extract the data we need from C recursivly. this will give us the 
* list of meeting that we need for one person. Then we call the getallmeetings
* for on the tail of C to get there meetings. we call sort append
* to append and sort the list of meetings as the recursion
* comes back up. The final result is a list that contains all 
* of the meetings in alphabetical order.
*/
getallmeetings(C,Z) :-
    [H|T] = C,
    [_|T2] = H,
    [H3|_] = T2,
    getallmeetings(T,Z2),
    sortappend(H3,Z2,Z).

/* Part 5b: participants */

participants(C,Z) :-
    getallmeetings(C,Temp),
    meetings(Temp,C,Z).

meetings([],_,[]).

meetings(Meetings,NameMeet,Return) :-
    [H|T] = Meetings,
    meetings(T,NameMeet,Temp),
    generatemeetinglist(H,NameMeet,Temp2),
    sort(Temp2),
    Temp3 = [[H,Temp2]],
    append(Temp3,Temp, Return).

generatemeetinglist(_,[],[]).

generatemeetinglist(Meeting,NameMeet,Return) :-
    %extracting the list of meetings
    [H|T] = NameMeet,
    [H2|T2] = H,
    [H3|_] = T2,
    generatemeetinglist(Meeting,T,Return2),
    (   member(Meeting,H3) ->
        append(Return2,[H2],Return)
    ;
        Return = Return2
    ).
   
/* Part 5c: osched */

osched(_,_,_,_).

/* Part 5d: xsched */

xsched(_,_,_,_).

