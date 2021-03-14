
-- This schema is a running example of EECS3421B
--  include NULL values

-- ===================================================================
-- York River Books, Inc., Book Vendor Database

-- Creation script
-- Parke Godfrey, Copyright 2001

-- Wenxiao Fu, adapted to PostgreSQL, 2020

-- ===================================================================
-- Drop the existing tables

drop view  PRETTY_PURCHASE CASCADE;
drop table YRB_BOOK CASCADE;
drop table YRB_CATEGORY CASCADE;
drop table YRB_CLUB CASCADE;
drop table YRB_CUSTOMER CASCADE;
drop table YRB_MEMBER CASCADE;
drop table YRB_OFFER CASCADE;
drop table YRB_PURCHASE CASCADE;
drop table YRB_SHIPPING CASCADE;
