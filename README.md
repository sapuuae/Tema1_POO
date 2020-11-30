# Tema1_POO
Sapunaru Andrei, 321CA
VideosDB, Programare Orientata pe Obiecte

O sa atasez link-ul de github: https://github.com/sapuuae/Tema1_POO.git. 
Precizez ca toate commit-urile au fost date pe branch-ul Develop, in final urmand sa dau
merge pe main.

#Am ales sa structurez tema in mai multe package-uri: <br />
    * in base, mi-am creat clase pentru filme, seriale, useri si actori. De asemenea,
    aici am clasa DataList, in care voi completa baza de date si voi verifica fiecare
    comanda data in input. 
    Clasele Movie, Show, User si Video sunt asemanatoare celor din fileio, dar am mai
    adaugat unele metode/campuri pentru a ma ajuta in rezolvarea cerintelor:
        * in user am adaugat un Map pentru a sti la ce filme a acordat rating si un
        camp pentru a vedea cate rating-uri a dat. De asemenea, metodele pentru comenzile
        favorite, view si rating le-am implementat in aceasta clasa:
            * pentru favorite, am verificat daca l-a vazut. Apoi, am verificat daca il are
            deja adaugat in lista de favorite. In final, daca primele doua nu se indeplinesc,
            l-am adaugat.
            * pentru view, am repetat aceiasi pasi ca la favorite, numai ca aici am modificat
            numarul de vizualizari din Map-ul history.
            * pentru rating, am verificat, initial, daca este film sau sezon. Am creat o metoda
            pe care am folosit overloading (addRatingVideo), pentru a o putea folosi la cele
            doua cazuri. De fiecare data cand user-ul dadea rating la un film/sezon dintr-un
            serial, adaugam titlul/titlul serialulului + numarul sezonului in map-ul in care
            salvam la ce video-uri s-a acordat rating.
        * in video am adaugat numarul de aparitii in favorite, de rating-uri primite,
        de vizualizari si indicele din baza de date. De asemenea, am adaugat o metoda
        pentru a calcula rating-ul, care va fi suprascrisa in clasele Movie si Show.
        * in MySeason este o clasa asemanatoare cu cea din Season, dar in care am adaugat
        o metoda care sa calculeze rating-ul mediu pentru fiecare sezon.
    * in usage, am implementat fiecare comanda, pentru Query si recomandari creand cate o clasa
     separata:
        * pentru query-urile pe actori:
            * la average, am creat o clasa noua, ActorsAndRating, in care am salvat doar numele
            si rating-ul acestora. Apoi, am parcurs lista de actori, am luat filmografia pentru
            fiecare, si am verificat ce filme dintre cele care in care a jucat au primit rating.
            Am creat o lista de obiecte ActorsAndRating, pe care urmeaza sa o sortez dupa rating
            si nume.
            * la awards, am creat o clasa noua, ActorsAndAwards, in care am salvat numele si
            numarul de premii primite. Pentru a vedea daca un actor are toate premiile, am
            parcurs map-ul de premii, verificand string-urile din input cu enum-urile de tip
            ActorsAwards folosindu-ma de metoda valueOf. In final, am sortat lista si am scris
            elementele in arrayResult.
            * la filter description, am folosit metoda split pentru a separa descrierea in cuvinte
            si sa verific daca in descrierea actorului exista exact termenii respectivi din input.
        * pentru query-urile pe video, am creat oscris metoda statica (checkTheVideo), care va verifica
        daca anul si genul date in input corespund cu cele ale video-ului verificat in momentul
        respectiv. Apoi, am abordat urmatoarele metode pentru rezolvare:
            * la rating, am parcurs lista de filme/seriale (in functie de ce se da in input),
            apoi le-am adaugat intr-o lista noua, pe care am sortat-o dupa rating si nume.
            * la favorite, am parcrus lista de filme/seriale, am verificat daca filmul se gaseste
            in lista de favorite a unui user, apoi il adaug la o lista finala, pe care o sortez
            dupa numarul de aparitii in listele de favorite si nume.
            * la longest, am sortat lista de filme/seriale dupa durata, apoi le-am adaugat in
            arrayResult.
            * la most viewed, am folosit parametrul din clasa Video (totalNumberOfViewed) pentru
            a sorta lista de filme/seriale creata dupa verificarea criteriilor (anul si genurile).
        * pentru query-ul pe user:
            * am folosit parametrul din clasa User (numberOfGivenRatings) pentru a sorta lista de
            utilizatori, apoi am adaugat in arrayResult userii respectivi.
        * pentru recomandari:
            * la standard, am obtinut User-ul pe baza username-ului, apoi am cautat primul film din
            baza de date care sa nu fie vazut de acel User, verificand filmele in map-ul de history
            al User-ului.
            * la bestunseen, am sortat lista de filme dupa rating, apoi am cautat filmul care poate
            fi recomandat.
            * la popular, am verificat tipul de subscriptie a userului, apoi am creat un hashmap
            in care am stocat genul si obiecte de tipul VideosWithGenresAndViews, in care
            aveam genul, o lista cu filmele de acel gen si numarul de vizualizari ale genului
            respectiv. Apoi, am creat o lista cu obiectele de acest tip, pe care am sortat-o dupa
            numarul de vizualizari ale fiecarui gen. In final, am parcurs lista sortata si am cautat
            printre filmele de la cel mai popular gen, mergand catre urmatoarele genuri in caz ca n-am
            gasit un film. 
            * la favorite, am adaugat toate filmele care au fost adaugate in liste de favorite intr-o
            noua lista, apoi le-am sortat descrescator dupa numarul de aparitii si am cautat un film
            care nu a fost vazut de user.
            * la search, am adaugat toate filmele care au genul respectiv intr-o noua lista,
            pe care am sortat-o crescator dupa rating si nume. Apoi, am adaugat filmele intr-o lista
            de tip String pe care o pun in arrayResult.
