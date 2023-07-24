from collections import deque
from collections import defaultdict
from heapq import heappush, heappop
from queue import Empty
actors = dict()
movies = dict()
 
# Actor klasse
class Actor:
    def __init__(self, name, nm_id):
        self.nm_id = nm_id
        self.name = name
        self.movies = set()
        self.edges = dict()

    def add_movie(self, movie):
        self.movies.add(movie)
 
    def get_movies(self):
        return self.movies
 
# Movie klasse
class Movie:
    def __init__(self, title, rating):
        self.title = title
        self.rating = rating
        self.actors_in_movie = set()
 
    def add_actor(self, actor):
        self.actors_in_movie.add(actor)
 
    def get_actors(self):
        return self.actors_in_movie
 
    def get_rating(self):
        return self.rating
 
def oppgEn():
    # lager movie objekter og poppulerer movie dictionairy
    inpMovies = open('testfiler/movies.tsv', encoding='utf-8').read()
    for l in inpMovies.splitlines():
        id, title, rate, _ = l.split('\t')
        movies[id] = Movie(title, rate)

    # lager actor objekter og poppulerer movie dictionairy
    inpActors = open('testfiler/actors.tsv', encoding='utf-8').read()
    for actor in inpActors.splitlines():
        id, name, *actor_movies = actor.split('\t')
        actors[id] = Actor(name, id)

        # Legger til skuespillere i filmobjectet tilhørende film de spiller i.
        # Dersom filmen ikke finnes, tar vi den ikke med.
        for movie in actor_movies:
            if movie in movies:
                actors[id].add_movie(movie)
                movies[movie].add_actor(id)
    kanter = 0
    for movie in movies:
        actors_movie = movies[movie].actors_in_movie
        # tar N*(N-1)/2, der N= antall noder, for å finne antall kanter
        kanter += int(len(actors_movie)*(len(actors_movie)-1)/2)
        return kanter

# implemeterer bfs
def shortest_path_parents(start,end=None):
    V= actors
    E= movies
    parents = {start : (None, None)}
    queue = deque([start])
 
    while queue:
        # henter ut fra køen, first in first out
        v = deque.popleft(queue)
 
        # ser først på alle kantene til en node
        for e in V[v].get_movies():
            #Finner alle noder som forbindes med kanten
            for u in E[e].get_actors():
 
                # finner vi sluttnoden vår så retuneres
                if u == end:
                    parents[u] = (v,e)
                    return parents
 
                # hvis noden ikke finnes i foreldrene fra før av så legges den
                # til, og settes igjen tilbake i køen
              
                if u not in parents:
                    parents[u] = (v, e)
                    queue.append(u)
 
    return parents
 
def find_shortest_path(start, end):
    parents=shortest_path_parents(start,end)
    v = (end, None)
    path = []
    
    while v[0]:
        path.append(v)
        v = parents[v[0]]
 
    return path[::-1]
 
def find_chillest_path(start, end=None):
    V= actors
    E= movies
    #V, E = G #setter actors dictionary til noder, og movies til kanter
    #legger til første steg i køen, dette vil ikke koste oss noe.
    Q = [(0, 0, start)]
    D = defaultdict(lambda: (float('inf'), float('inf'))) #Oppretter en defaultdict D for (Antall steg, rating).
 
    D[start] = (0,0) #setter steg til start
 
    parents_path = {start : (None, None)}
    final_step = float('inf')
 
    while Q:
        step, rating, v = heappop(Q)
 
        step += 1
        if final_step < step:
            return parents_path
 
        for e in V[v].get_movies():
            movie_rating = (10-float(E[e].get_rating()))
            total_rating = rating + movie_rating
            # oppdaterer kosten til den aktualle kanten
 
            # sjekker nodene som den aktuelle kanten leder til
            for u in E[e].get_actors():
                if end == u:
                    final_step = step
                # finner den kanten som koster oss minst
                if step <= D[u][0] and total_rating <= D[u][1] and v != u:
                    D[u] = (step, total_rating)
                    heappush(Q, (step, total_rating, u))
                    parents_path[u] = (v, e)
 

    return parents_path
 
def components_and_size():
    V = actors
    comp_size = dict()
    key_V = list(V.keys())
 
    while key_V:
        start = key_V[0]
        parents = shortest_path_parents(start)
 
        if len(parents) in comp_size:
            comp_size[len(parents)] = comp_size[len(parents)] + 1
        else:
            comp_size[len(parents)] = 1
        key_V = [x for x in key_V if x not in parents]
 
    return comp_size
 
def nice_print(start, path, weight= False):
    V= actors
    E= movies
    w = 0
 
    print()
    print(V[start[0]].name)
 
    for i in range(len(path)-1):
        # finner rating fra film-objekt i kantene
        rating = E[path[i][1]].rating
        # finner tittel fra film-objekt i kantene
        title = E[path[i][1]].title
        # finner navn på skuespiller fra noder
        next_actor = V[path[i+1][0]].name
        # setter vekt
        w += (10-float(rating))
 
        print(f'===[ {title} ({rating}) ] ===> {next_actor}')
    if weight:
        print(f'Total weight: {round(w,1)}')
 
def main():
    print("oppgave 1")
    kanter = oppgEn()
    print(" ")
    print("Nodes: " + str(len(actors)))
    print("Edges: " + str(kanter))
    print(" ")

    print("Oppgave 2")
    path = [['nm2255973','nm0000460'],['nm0424060','nm0000243'],
    ['nm4689420','nm0000365'],['nm0000288','nm0001401'],['nm0031483','nm0931324']]
    for x in path:
        y=find_shortest_path((x[0]),x[1])
        nice_print(y[0],y)

    print(" ")
"""
    print("Oppgave 3")
    for x in path:
        y=find_shortest_path(find_chillest_path(x[0]),x[1])
        nice_print(y[0],y,weight=True)

    print(" ")
    print("oppgave 4: ")
    liste = components_and_size()
    for key, val in reversed(sorted(liste.items())):
        print(f'There are {val} components of size {key}')  
"""
main()