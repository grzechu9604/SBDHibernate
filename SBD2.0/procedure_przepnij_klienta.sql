
create or replace procedure przepnij_klienta(klient_z in number, klient_na in number)
is 
    update_pojazdy_query varchar2(1000) := 'update pojazdy set KLIENCI_ID = :klient_na where KLIENCI_ID = :klient_z';
    delete_from_klient varchar2(1000) := 'delete from KLIENCI where id = :klient_z';
    delete_from_osoby varchar2(1000) := 'delete from osoby where id = :klient_z';
begin
    
    execute IMMEDIATE update_pojazdy_query using klient_na, klient_z;
    execute IMMEDIATE delete_from_klient using klient_z;
    execute IMMEDIATE delete_from_osoby using klient_z;
    
end;
