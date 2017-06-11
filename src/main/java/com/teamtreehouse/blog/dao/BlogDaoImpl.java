package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BlogDaoImpl implements BlogDao {

  private List<BlogEntry> blogEntries;

  public BlogDaoImpl() {
    blogEntries = new ArrayList<>();
  }

  @Override
  public boolean addEntry(BlogEntry blogEntry) {
    return blogEntries.add(blogEntry);
  }

  @Override
  public void deleteEntry(BlogEntry blogEntry) {
    blogEntries.remove(blogEntry);
  }

  @Override
  public List<BlogEntry> findAllEntries() {
    return new ArrayList<>(blogEntries);
  }

  @Override
  public BlogEntry findEntryBySlug(String slug) {
    return blogEntries.stream()
        .filter(blogEntry -> blogEntry.getSlug().equals(slug))
        .findFirst()
        .orElseThrow(NotFoundException::new);
  }
}
