create or replace function trigger_func()
returns trigger as
$$begin
if(select teaches.ID 
   from teaches natural join section
   where teaches.ID=new.ID and teaches.course_id = new.course_id and
   section.year = new.year and teaches.sec_id!=new.sec_id and 
   teaches.semester = new.semester
  ) is not null
   then return NULL;
   else return new;
end if;
end;$$
LANGUAGE plpgsql;

CREATE or REPLACE TRIGGER trigger_func BEFORE INSERT ON teaches
    					FOR EACH ROW EXECUTE PROCEDURE trigger_func();