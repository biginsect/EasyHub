package com.biginsect.easyhub.constant;

/**
 * github开放API
 * @author biginsect
 * @date 2018/1/8
 */

public class ServerApi {
    private ServerApi(){
    }
    public static final String BASE_URL = "https://api.github.com/";

    public static final String CURRENT_USER_URL = BASE_URL + "user";
    public static final String CURRENT_USER_AUTHORIZATIONS_HTML_URL = "https://github.com/settings/connections/applications{/client_id}";
    public static final String AUTHORIZATIONS_URL = "https://api.github.com/authorizations";
    public static final String CODE_SEARCH_URL = "https://api.github.com/search/code?q={query}{&page,per_page,sort,order}";
    public static final String COMMIT_SEARCH_URL = "https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}";
    public static final String EMAILS_URL = CURRENT_USER_URL + "/emails";
    public static final String EMOJIS_URL = "https://api.github.com/emojis";
    public static final String EVENTS_URL = BASE_URL + "events";
    public static final String FEEDS_URL = "https://api.github.com/feeds";
    public static final String FOLLOWERS_URL = CURRENT_USER_URL + "/followers";
    public static final String FOLLOWING_URL = CURRENT_USER_URL + "/following{/target}";
    public static final String GISTS_URL = BASE_URL + "gists{/gist_id}";
    public static final String HUB_URL = "https://api.github.com/hub";
    public static final String ISSUE_SEARCH_URL = "https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}";
    public static final String ISSUES_URL = BASE_URL + "issues";
    public static final String KEYS_URL = "https://api.github.com/user/keys";
    public static final String NOTIFICATIONS_URL = BASE_URL + "notifications";
    public static final String ORGANIZATION_REPOSITORIES_URL = "https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}";
    public static final String ORGANIZATION_URL = "https://api.github.com/orgs/{org}";
    public static final String PUBLIC_GISTS_URL = BASE_URL + "gists/public";
    public static final String RATE_LIMIT_URL = "https://api.github.com/rate_limit";
    public static final String REPOSITORY_URL = "https://api.github.com/repos/{owner}/{repo}";
    public static final String REPOSITORY_SEARCH_URL = "https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}";
    public static final String CURRENT_USER_REPOSITORIES_URL = "https://api.github.com/user/repos{?type,page,per_page,sort}";
    public static final String STARRED_URL = CURRENT_USER_URL+ "/starred{/owner}{/repo}";
    public static final String STARRED_GISTS_URL = BASE_URL + "gists/starred";
    public static final String TEAM_URL = BASE_URL + "teams";
    public static final String USER_URL = BASE_URL + "users/{user}";
    public static final String USER_ORGANIZATIONS_URL = "https://api.github.com/user/orgs";
    public static final String USER_REPOSITORIES_URL = "https://api.github.com/users/{user}/repos{?type,page,per_page,sort}";
    public static final String USER_SEARCH_URL = "https://api.github.com/search/users?q={query}{&page,per_page,sort,order}";

}
