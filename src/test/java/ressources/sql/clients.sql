insert into clients (id, name) values
  ('8a1625ae-1fae-4839-9dee-fec3857a8806', 'Ivan');

insert into accounts (id, devise, client_id) values
  ('c65b7428-9ce9-4fd9-8640-9278fd6a49b4', 'euros', '8a1625ae-1fae-4839-9dee-fec3857a8806');

insert into operations (id, montant, operation_type, account_id) values
  ('78f0ca95-f15d-4e8f-9450-309adc2984bb', 200.0, 'DEPOSER', 'c65b7428-9ce9-4fd9-8640-9278fd6a49b4');