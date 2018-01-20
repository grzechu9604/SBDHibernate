create or replace function LICZ_CENE (id_zlecenia in Number) return number is
cena_sugerowana number(9,2);
query_to_execute varchar2(1000) := 'select sum(kwota) as sugerowana_cena from( select 
sum(NVL(c.cena_sprzedazy, c.cena_zakupu * 1.3)) as kwota
from ZLECENIA z
join CZESCI c on z.NR_NAPRAWY = c.ZLECENIE_NR_NAPRAWY
where z.NR_NAPRAWY = 10
union all
select STAWKA_GODZINOWA * dni * 4 as kwota from (
select e.STAWKA_GODZINOWA, NVL(
z.DATA_ODBIORU - z.DATA_WPLYWU, z.PLANOWANE_ZAKONCZENIE - z.DATA_WPLYWU) as dni
from ZLECENIA z
join ZLECENIA_DZIALOW zd on zd.NR_NAPRAWY = z.NR_NAPRAWY
join DZIALY d on d.NR_DZIALU = zd.NR_DZIALU
join PRACOWNICY p on d.ID_SZEFA = p.ID
join ETATY e on e.NAZWA = p.NAZWA_ETATU where z.NR_NAPRAWY = :nr_naprawy))';
begin

    EXECUTE IMMEDIATE query_to_execute INTO cena_sugerowana USING id_zlecenia;
    return cena_sugerowana;
    EXCEPTION
        WHEN NO_DATA_FOUND Then return 0;
end;