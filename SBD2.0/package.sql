create or replace PACKAGE paczka_z_bazy AS 
    procedure przepnij_klienta(klient_z in number, klient_na in number);
    function LICZ_CENE (id_zlecenia in Number) return number;
END;

CREATE OR REPLACE PACKAGE BODY paczka_z_bazy AS  

   procedure przepnij_klienta(klient_z in number, klient_na in number)
    is 
        update_pojazdy_query varchar2(1000) := 'update pojazdy set KLIENCI_ID = :klient_na where KLIENCI_ID = :klient_z';
        delete_from_klient varchar2(1000) := 'delete from KLIENCI where id = :klient_z';
        delete_from_osoby varchar2(1000) := 'delete from osoby where id = :klient_z';
    begin

        execute IMMEDIATE update_pojazdy_query using klient_na, klient_z;
        execute IMMEDIATE delete_from_klient using klient_z;
        execute IMMEDIATE delete_from_osoby using klient_z;

    end; 

    function LICZ_CENE (id_zlecenia in Number) return number is
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
END ; 