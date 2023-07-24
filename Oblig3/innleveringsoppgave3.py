from collections import deque
from heapq import heappush, heappop

skuespillere = dict()
filmer = dict()


class Skuespiller:
    def __init__(self, name, nm_id):
        self.navnID = nm_id
        self.navn = name
        self.filmer = set()
        self.kanter = []

    def leggTilFilm(self, movie):
        self.filmer.add(movie)

    def leggTilKant(self, actor, movie):
        self.kanter.append([actor, movie])

    def hentFilm(self):
        return self.filmer

    def hentNavnID(self):
        return self.navnID

    def hentKant(self):
        return self.kanter

    def hentNavn(self):
        return self.navn


class Movie:
    def __init__(self, title, rating):
        self.title = title
        self.rating = rating
        self.actors_in_movie = set()

    def leggTilS(self, actor):
        self.actors_in_movie.add(actor)

    def hentS(self):
        return self.actors_in_movie

    def hentRating(self):
        return self.rating

    def hentNavn(self):
        return self.title


def oppgaveEn():
    inpFilmer = open('testfiler/movies.tsv', encoding='utf-8').read()
    for linje in inpFilmer.splitlines():
        id, tittel, rating, _ = linje.split('\t')
        filmer[id] = Movie(tittel, rating)


    inpSS = open('testfiler/actors.tsv', encoding='utf-8').read()
    for SS in inpSS.splitlines():
        id, navn, *SSfilm = SS.split('\t')
        skuespillere[id] = Skuespiller(navn, id)

        for filmen in SSfilm:
            if filmen in filmer:
                skuespillere[id].leggTilFilm(filmen)
                filmer[filmen].leggTilS(id)

    for SS in skuespillere:
        for filmen in skuespillere[SS].hentFilm():
            for coSS in filmer[filmen].hentS():
                if skuespillere[coSS] != skuespillere[SS]:
                    skuespillere[SS].leggTilKant(skuespillere[coSS], filmer[filmen])

def Dijkstra(start):
    Besokt = set()
    Heap = []
    Distanse = dict()
    foreldre = {start: None}

    for SS in skuespillere:
        Distanse[SS] = float('inf')
        heappush(Heap, (Distanse[SS], SS))
    Distanse[start] = 0
    heappush(Heap, (Distanse[start], start))

    while len(Heap) > 0 and len(Besokt) < len(
            skuespillere):
        avstand, nermestSS = heappop(Heap)
        # print(str(avstand), closestActor)
        if nermestSS not in Besokt:  # sjekker om nærmeste skuespiller (node) ikke er besøkt
            Besokt.add(nermestSS)  # dersom den ikke er besøkt legges den til
            for kant in skuespillere[nermestSS].hentKant():  # går igjennom alle kanter ut fra noden
                cost = avstand + 10 - float(kant[
                                                1].hentRating())  # finner kostnadden for å komme seg til den nye kanten edge[1] gir kostnaden for kanten
                # print(cost)
                if cost < Distanse[kant[0].hentNavnID()]:  # henter actor objektet
                    Distanse[kant[0].hentNavnID()] = cost
                    heappush(Heap, (cost, kant[0].hentNavnID()))
                    foreldre[kant[0].hentNavnID()] = [nermestSS, kant[1]]
    return foreldre  # gir den biligste veien å finne alle elementer [actors_id] = distance


def FinesteVei(start, slutt):
    parents = Dijkstra(start)
    teller = parents[slutt]

    kost = 0
    utskrift = []

    while teller[0] != None:
        pris = 10 - float(teller[1].hentRating())
        kost += pris
        utskrift.append(str(skuespillere[teller[0]].hentNavn()) + "\n" + "]===> " + teller[1].hentNavn() + " " + str(
            teller[1].hentRating()))
        teller = parents[teller[0]]
        if (teller[0] == start):
            pris = 10 - float(teller[1].hentRating())
            utskrift.append("===[" + teller[1].hentNavn() + " " + str(teller[1].hentRating()) + "]===>")
            kost += pris
            utskrift.reverse()
            print(skuespillere[start].hentNavn())
            for each in utskrift:
                print(each)
            print("]===>" + skuespillere[slutt].hentNavn())
            print("Total weight: " + str(kost) + "\n")
            return


def AntKompOgStrls():
    listeMedCom = dict()
    listeMedKeys = list(skuespillere.keys())
    while listeMedKeys:
        parents = KortVeiForeldre(listeMedKeys[0])
        if len(parents) not in listeMedCom:
            listeMedCom[len(parents)] = 1
        else:
            listeMedCom[len(parents)] = listeMedCom[len(parents)] + 1
        listeMedKeys = [x for x in listeMedKeys if x not in parents]
    for nokkel, verdi in reversed(sorted(listeMedCom.items())):
        if verdi == None:
            break
        else:
            print(f'There are {verdi} components of size {nokkel}')


def FinnKortestVei(start, end):
    parents = KortVeiForeldre(start, end)
    v = (end, None)
    path = []

    while v[0]:
        path.append(v)
        v = parents[v[0]]
    i = len(path[::-1]) - 1
    ix = 0
    for each in path[::-1]:
        print(skuespillere[each[0]].hentNavn())
        if (ix < i):
            print("===[" + filmer[each[1]].hentNavn() + " " + filmer[each[1]].hentRating() + "] ===>")
        ix += 1
    print("\n")


def KortVeiForeldre(start, end=None):
    skuSpiller = skuespillere
    filmen = filmer
    foreldre = {start: (None, None)}
    queue = deque([start])

    while queue:
        verdien = deque.popleft(queue)

        for elmnt in skuSpiller[verdien].hentFilm():
            for ullim in filmen[elmnt].hentS():

                if ullim == end:
                    foreldre[ullim] = (verdien, elmnt)
                    return foreldre



                if ullim not in foreldre:
                    foreldre[ullim] = (verdien, elmnt)
                    queue.append(ullim)

    return foreldre


def main():
    print("Oppgave 1")
    oppgaveEn()

    print("Nodes: " + str(len(skuespillere)))
    totalEdges = 0
    for actor in skuespillere:
        totalEdges += len(skuespillere[actor].hentKant())
    print("Edges: " + str(totalEdges / 2))
    print("")
    print("Oppgave 2")
    path = [['nm2255973', 'nm0000460'], ['nm0424060', 'nm0000243'],
            ['nm4689420', 'nm0000365'], ['nm0000288', 'nm0001401'], ['nm0031483', 'nm0931324']]
    for x in path:
        FinnKortestVei(x[0], x[1])

    print(" ")

    print("Oppgave 3")
    for x in path:
        FinesteVei(x[0], x[1])

    print("Oppgave 4")
    AntKompOgStrls()


main()