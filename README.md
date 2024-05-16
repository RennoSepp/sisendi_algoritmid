# Sisendi generaatorid

Programmi skoop on pakkuda lihtsat võimalust genereerida sisendeid kahendotsimispuude, AVL-puude ja kuhjade algoritmidele. Kuna UI eesmärk on tulemi visualiseerimine on programmi efektiivseks kasutamiseks tarvilik anda selle vaid sobivaid sisendeid.

### Järjendi lugemine kahendotsimispuuks

Elementide arv järjendis on otseselt seotud raskusparameetriga – alljärgnev suhe on soovitatav sisend

Elementide arv -> Raskusparameeter

1	-> 0

2	-> 1

3	-> 2-3

4 -> 4-6

5	-> 6-10

6	-> 8-15

7	-> 10-21

8	-> 13-28

9	-> 16-36

10 -> 19-45

11 -> 22-45

12 -> 25-45

13 -> 28-45

14 -> 35-60

15 -> 38-60

### Elementide eemaldamine kahendotsimispuust

Selle jaoks, et tagada, et järjendis eksisteeriks soovitud arv kindlate struktuuridega ahelaid on programmi efektiivseks kasutamiseks 3 soovitus:

Tippude arv > 5

Eemaldatavate elementide arv < ⅓ * Tippude arv

Raskusparameeter <= Tippude arv

### Elementide lisamine AVL-puusse

Kuna suvalisi tippe puule genereeritakse vahemikus 1-100 on soovitatav hoida puu Tippude arvu alla 25.

### Elementide eemaldamine AVL-puust

Selle jaoks, et tagada, et järjendis eksisteeriks soovitud arv kindlate struktuuridega ahelaid on programmi efektiivseks kasutamiseks 3 soovitus:

Tippude arv > 10

Eemaldatavate elementide arv < ⅓ * Tippude arv

Raskusparameeter <= Eemaldatavate elementide arv + 2

### Järjendi kuhjastamine
Elementide arv järjendis on otseselt seotud raskusparameetriga – alljärgnev suhe on soovitatav sisend

Elementide arv		Raskusparameeter

1	-> 0

2	-> 1

3	-> 2-3

4	-> 4-5

5	-> 7-8

6	-> 8-11

7	-> 10-14

8	-> 13-17

9	-> 15-21

10 -> 19-25

11 -> 22-28

12 -> 23-33

13 -> 26-36

14 ->	29-41

15 -> 32-45

### Järjendi sorteerimine kuhjameetodil

Elementide arv järjendis on otseselt seotud raskusparameetriga – alljärgnev suhe on soovitatav sisend

Elementide arv		Raskusparameeter

1	-> 0

2	-> 1

3	-> <3

4	-> <4

5	-> <4

6	-> <5

7	-> <5

8	-> <8

9	-> <8

10 -> <9

11 -> <9

12 -> <11

13 -> <11

14 ->	<12

15 ->	<12


