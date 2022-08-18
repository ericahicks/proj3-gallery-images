-- script can be used in local dev MySQL and H2
create table listing (listing_id varchar(36) not null, manufacturer_recommended_age varchar(255), product_brand varchar(255), created_by varchar(255), created_date timestamp, listing_image_src varchar(255), last_modified_by varchar(255), last_modified_date timestamp, listing_language_code varchar(255) not null, listing_price double, listing_title varchar(255), listed_in_country integer, listing_details_id varchar(36), username varchar(255), primary key (listing_id)); 
create table listing_categories (listing_id varchar(36) not null, category_id integer not null, primary key (listing_id, category_id)); 
create table listing_category (listing_category_id integer auto_increment, created_by varchar(255), created_date timestamp, listing_category_description varchar(255), last_modified_by varchar(255), last_modified_date timestamp, listing_category_name varchar(255) not null, primary key (listing_category_id)); 
create table listing_country (country_id integer auto_increment, default_language_code varchar(255) not null, country_name varchar(255) not null, primary key (country_id)); 
create table listing_details (listing_details_id varchar(36) not null, created_by varchar(255), created_date timestamp, description BLOB, dimensions_description varchar(255), last_modified_by varchar(255), last_modified_date timestamp, product_material varchar(255), release_date date, safety_information BLOB, warranty_information BLOB, weight_description varchar(255), primary key (listing_details_id)); 

create table listing_variations (listing_variation_id varchar(36) NOT NULL, variation_type VARCHAR(45) null, variation_description VARCHAR(255) NULL, listing_id varchar(36) NOT NULL, PRIMARY KEY (listing_variation_id));
alter table listing_variations add CONSTRAINT fk_LISTING_VARIATIONS_listing1 FOREIGN KEY (LISTING_ID) REFERENCES listing(listing_id);

create table seller (username varchar(255) not null, primary key (username));

create table listing_gallery_images(listing_gallery_image_id varchar(36) not null, image_src varchar(255), listing_detail_id varchar(36), primary key (listing_gallery_image_id));
alter table listing_gallery_images add constraint fk_listing_img_listing_dets foreign key (listing_detail_id) references listing_details(listing_details_id);

CREATE TABLE listing_coupon (coupon_id INT8 NOT NULL auto_increment, coupon_code VARCHAR(45), dollar_off BOOLEAN, percent_off BOOLEAN, discount_amount INT, seller_id VARCHAR(255) NOT NULL, PRIMARY KEY (coupon_id));

alter table listing_category add constraint UK_qh0gjm15l7vssj3hvaeqk6yd2 unique (listing_category_name); 
alter table listing_country add constraint UK_ainm41hvwsa0a45bcprj39wbw unique (country_name); 
alter table listing add constraint FK5e4efuej20inkv425i5gmf21s foreign key (listed_in_country) references listing_country(country_id); 
alter table listing add constraint FKo0myryssxi2vh3hxbkfccsn6g foreign key (listing_details_id) references listing_details(listing_details_id); 
alter table listing add constraint FK9mt4yl5l7mvtm3cayrhvgkij5 foreign key (username) references seller(username); 
alter table listing_categories add constraint FKc8j46vtwww35mbj1cnmvy7psg foreign key (category_id) references listing_category(listing_category_id); 
alter table listing_categories add constraint FKc9mh6m2uk9b19bcs1fc6qcwhl foreign key (listing_id) references listing(listing_id);
alter table listing_coupon add constraint seller_id foreign key (seller_id) references seller(username);