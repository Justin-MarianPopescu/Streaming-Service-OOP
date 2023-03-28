## Proiect POO TV - Etapa a II-a :movie_camera:

### Explicatii ale actiunilor etapei II :clipboard:

**Update, aparitie notifications & subscribe genres** :newspaper:

La toti userii apar un array list de notificari & subscriptii dupa anumite genuri de filme preferate sau filme deja detinute.
De fiecare data cand apar modificari in baza de date adaugare/stergere film, utilizatorului i se va trece in lista de notificari. Notificarile fiecarui se pastreaza si dupa deconectarea utilizatorului, asupra listei de notificari se vor efectua doar operatii de adaugare notificari.
Cand se da subscribe la un anumit gen de filme, el se trece in lista de genuri. Iar lista de genuri se pastreaza pentru fiecare user chiar si dupa deconectarea acestuia.

***subscribe action UserUI package***: abonarea unui utilizator la un anumit gen de filme la care este abonat. Utilizatorul se aboneaza la un singur gen de filme prin aceasta actiune. Fiecare subscriptie sa pastreaza de fiecare data cand user-ul iese si intra pe platforma. La fiecare subscriptie la un anumit gen de filme se pastreaza abonarile realizate.

***recommendation action UserUI package***:  userii premium beneficieaza de recomandari de filme in functie de un top al genurile apreciate de utilizatori, nr de like-uri date de utilizatori, in cazul de egalitate, sortarea se face crescator dupa numele genurilor. Fileme oferite spre recomandare nu au fost de utilizatorul premium inainte.

***back action case in OutputActions***: inversul actiunii de change page, in loc sa navighez inainte cu paginile conform workflow-ului, ne reintoarcem la paginile deja vizitate, actiunea se executa pentru ultimul user current autentificat. Ne putem intoarce cat timp mai avem pagini de vizitat sau daca nu ne aflam pe paginile de login/register care genereaza eroare astfel actiunea de intoarcere.

***database in Database class***: add & delete operations pe filme in lista de filme existenta in baza de date. In cazul aparitiei de add & delete apar noi notificari pentru useri, diferentele la aceste 2 operatii modifica lista de notificari ale utilizatorilor & lista de filme oferite la subscribe pe genuri dorite.

### Design Patterns proiect :wrench:

Design Pattern-urile care au fost folosite in realizarea finala a proiectului au fost gandite astfel incat ca proiectul final sa beneficieze de imbunatatiri substantiale ca si numar de linii, alegeri nume variabile & obiecte, eliminarea repetarilor de secvente de cod si a erorilor aduse din cauza suprascrierea sau pierderea vechiilor referinte ale obiectelor.

In gasirea unor solutii generale, reutilizabile, la probleme care apar frecvent in contexte similare. Design-urile au fost realizate in asa maniera cat sa fie usor de gasit  in pachete. O parte dintre pachete poarta numele design-ului ales spre implementare, astfel am creat pachete separate care contin clase & interfete care doar implementeaza design-ul cu care au fost predestinate, acum sa trecem la fapte :smile::

* ***Singleton (Lazy Instantiation)*** :construction_worker: , astfel, construcutorul privat restricitioneaza instantierea din alte clase, dar si din cauza folosirii foarte dese a acestei clase, prin urmare cu Singleton scap de dependentele foarte multe de la fiecare clasa din proiect, care depinde de o anumite clase in cauza, folosit pentru:
    * **UserCurrent** (*class*) -utilizatorul curent autentificat in cont, pe parcursul proiectul a trebuit de mai multe ori sa am la indemana userul curent autentificat, fiind folosit in marea majoritatea actiunilor pe parcurusul proiectului, atribuirea aduce pierdieri ale valorilor retinute cand ne ducem de la o metoda la alta.
    * **InstanceAction** (*class*) - la cele mai multe actiuni, aici am realizat scrierea in fisierul creat **result_basic** pentru executarea actiunilor din package **in**.   Actiunile de scriere sunt urmatoarele, o mare parte dintre metode au fost realizate ca o modalitate de micsora codul duplicat, actiunile de listare sunt pentru:
    1. Detalii despre lista de filme, cat si pentru fiecare film luat individaul.
    2. Detalii despre utilizator:
        * **credentials** - date despre contul utilizatorului
        * **movieList** - lista de filme pe care poate sa o vada, cu conditia ca filmul sa nu fie banned in tara sa
        * **rated**, **watched**, **liked**, **purchased** - listele cu filmele detinute de utilizator listele se modifica constant, in urma actiunilor specifice de la Etapa I
    3. Listele de filme achiziționate, vizionate, apreciate și evaluate de utilizator
    4. Un nou film în lista de filme cu toate filmele existente, de la *see details*
    5. Detalii cu toate informațiile despre utilizatorul autentificat
    6. Toate notificarilor fiecarui utilizator
    7. Toate filmele existente in tara utilizatorului pe baza unui criteriu de sortare
    8. Toate filmele, inclusiv cel nou, dacă a fost adaugat in baza de date
    9. Toate informatiilor înregistrate despre noul utilizator
    10. Toate filmele care încep cu un șir dat startsWith
    11. Toate filmele care nu sunt interzise într-o țară
    12. Nod cu toate informatile despre film, cat si pentru utilizator
    13. La sfârșitul lui *result_basic* de bază recomandarile utilizatorului curent premium

* **Strategy** :chart_with_upwards_trend:, selectez astfel algoritmi potriviti cu un anumit context, pentru fiecare tip de actiune de selectia a filmelor la metoda **showFilter** la run-time. Astfel, nu mai implementez de fiecare data un singur algoritm direct care sa-mi rezolve sortarea, filtrarea & stergerea filmelor, codul care se ocupa de listarea filmelor urmeaza criterile de filtrare de la etapa I.

* **Visitor** :oncoming_bus:, creez astfel algoritmi separati care opereaza un anumit tip de obiect. In cazul de fata obiectele sunt oferite de clasele **PurchaseAccount** & **PurchaseTokens**. Clasa PurchaseAccount foloseste  metoda **buyPremium** prin care cumpar un cont premiu sau utilizatorul devine din STANDARD in PREMIUM. Iar clasa PurchaseTokens foloseste upgrade **buyTokens** prin care cumpar un anumit numar de token-uri cerut.

* **Factory** :factory:, permit astfel claselor sa le aman instantierea obiectelor care se ocupa de introducerea, stergerea & modificarea listelor de filme cumparate, vizionate, apreciate si ratate. Folosesc o interfata care contine metoda **actionMovie** care va fi implementata de o metoda din fabrica care decide ce obiect sa creeze **FactoryMovie**. Construcutorii sunt astfel cat se poate de generice, nu sunt polimorfi, permit astfel subclaselor **Like**, **Watch**, **Rate**, **Purchase** sa fie create, aduaga astfel un comporatament polimorf la instantiere. Obiectele sunt create abia atunci cand se selecteaza tipul de actiune de la pagina **see details**.

## Feedback Etapa a II-a & Echipa POO :speech_balloon:

### Etapa a II-a

Comparativ fata de Etapa I a fost mult mai simpla, nu a trebuit decat implementez decat 4 noi actiuni: back, subscribe, database add/delete & recommendation pentru userul premium curent autentificat, cea mai mare provocare a fost data de actiunea de **back**, a trebuit sa ma gandesc la o modalitate de intoarcere la pagina anterioara fara sa modific paginile anterioare care respecta structura Etapei I, flow-ul de pagini, mai pe scurt, paginile pe care ma aflu curent pana sa ma reintorc la paginile de dinainte salvate in lista. In rest, celelalte 3 actiuni au fost suficient de clare cat sa pot sa rezolv fara mari dificultati.

### Echipa POO :speak_no_evil:

In viitor va recomand sa nu mai modificati inputul temei, nu ma deranjeaza daca modificati referintele, outputul, in cazul in care exista nedumeriri sau greseli de workflow, de logica in program. Sa fiu sincer in cazul meu nu a reprezentat o dificultate mare noile modificari la formatul de **subscribe** & output-ul la actiunea de **watch**, la care pot sa vad un de mai multe ori fara sa primim eroare. In cazul lui **subscribe** nu a trebuit decat sa mai adaug inca un case statement la **see details** pentru actiunile **on page**, iar la **watch** sa sterg o conditie dintr-un if.

Norocul meu a fost ca asa mi-am organizat proiectul caci daca nu cele 30 de min se transformau in cateva ore bune :satisfied:. Acum, nu stiu ce sa zic in cazul altor colegi... Adica, cand primesc o tema, ma astept sa fie cel putin aproximativ gata, fara sa apara modifcari destul de semnificative, totusi sa termini si dupa sa vezi ca trebuie sa o reiei de la capat nu este un sentiment placut.

Va mai recomand, sa tineti legatura cu restul de echipe de la alte materii SO, AA, etc... deoarece avem si aici suficiente teme & proiecte de predat la timp, ar fi bine sa distantiati mai bine DEADLINE-urile la teme & proiecte cat sa nu apara suprapuneri, deobicei in ultimul moment apare inspiratia :stuck_out_tongue_winking_eye:.

