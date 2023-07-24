from collections import deque
from collections import defaultdict
from heapq import heappush, heappop
from queue import Empty
actors = dict()
movies = dict()
 
class Actor:
    def __init__(self, name, nm_id):
        # endre navn på variabler og metode navn
        self.nm_id = nm_id
        self.name = name
        self.movies = set()
        self.edges = dict()

    #adders
    def addMovie(self, movie):
        self.movies.add(movie)

    #getters
    def get_movies(self):
        return self.movies

# Movie klasse
class Movie:
    # endre variabel navn og funksjonsnavn
    def __init__(self, title, rating):
        self.title = title
        self.rating = rating
        self.actors_in_movie = set()

    #adders
    def add_actor(self, actor):
        self.actors_in_movie.add(actor)

    #getters
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
                actors[id].addMovie(movie)
                movies[movie].add_actor(id)
    kanter = 0
    for movie in movies:
        actors_movie = movies[movie].actors_in_movie
        # tar N*(N-1)/2, der N= antall noder, for å finne antall kanter
        kanter += int(len(actors_movie)*(len(actors_movie)-1)/2)
    return kanter




def shortest_path_parents(start,end=None):
    V= actors
    E= movies
    parents = {start : (None, None)}
    queue = deque([start])
    path = []
    k = (end, None)
 
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
                    #return parents
 
                # hvis noden ikke finnes i foreldrene fra før av så legges den
                # til, og settes igjen tilbake i køen
              
                if u not in parents:
                    parents[u] = (v, e)
                    queue.append(u)
                    #return path
    
    while k[0]:
        path.append(k)
        k = parents[k[0]]
 
    return path[::-1]
 
    #return parents




def find_shortest_path(parents, end):
    v = (end, None)
    path = []
    
    while v[0]:
        path.append(v)
        v = parents[v[0]]
 
    return path[::-1]

 
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
    #print(shortest_path_parents('nm2255973','nm0000460'))
    path = [['nm2255973','nm0000460'],['nm0424060','nm0000243'],
    ['nm4689420','nm0000365'],['nm0000288','nm0001401'],['nm0031483','nm0931324']]
    for x in path:
       # y=find_shortest_path(shortest_path_parents(x[0]),x[1])
        y=shortest_path_parents(x[0],x[1])
        nice_print(y[0],y)
       

    print(" ")
    
  

main()