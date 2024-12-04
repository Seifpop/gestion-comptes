-- noinspection SqlNoDataSourceInspectionForFile

-- accounts
INSERT INTO account (name, number) VALUES ('John', 'E001');
INSERT INTO account (name, number) VALUES ('Jane', 'E002');
INSERT INTO account (name, number) VALUES ('Alice', 'E003');

-- operations
INSERT INTO operation (id, amount, description, date, account_number) VALUES (nextval ('hibernate_sequence'), 200.0, 'Dépôt', '2024-12-03', 'E001');
INSERT INTO operation (id, amount, description, date, account_number) VALUES (nextval ('hibernate_sequence'), 100.0, 'Dépôt', '2024-12-02', 'E002');
INSERT INTO operation (id, amount, description, date, account_number) VALUES (nextval ('hibernate_sequence'), 300.0, 'Dépôt', '2024-12-01', 'E003');

INSERT INTO operation (id, amount, description, date, account_number) VALUES (nextval ('hibernate_sequence'), -50, 'Retrait', '2024-12-03', 'E001');
INSERT INTO operation (id, amount, description, date, account_number) VALUES (nextval ('hibernate_sequence'), -30.0, 'Achat en magasin', '2024-12-02', 'E002');
