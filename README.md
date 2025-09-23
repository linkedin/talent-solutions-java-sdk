# Linkedin-Talent-Solutions-SDK-Java

### **The official [LinkedIn Talent](https://learn.microsoft.com/en-us/linkedin/talent/?view=li-lts-2025-04) Java client library.**


## **Installation**


### **Requirements**



* Java 1.8 or later


### **Gradle users**

Add this dependency and repositories information to your project's build.gradle file:

```build.gradle
repositories {
    maven {
        url "https://linkedin.jfrog.io/artifactory/talent-solutions-java-sdk/"
    }
    mavenCentral()
}

dependencies {
    implementation "com.linkedin:talent-solutions-java-sdk:1.0.0-alpha"
}
```


### **Maven users**

Add this dependency and repositories information to your project's POM.xml:

```pom.xml
<repositories>
    <repository>
        <id>public-jfrog</id>
        <url>https://linkedin.jfrog.io/artifactory/talent-solutions-java-sdk/</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.linkedin</groupId>
    <artifactId>talent-solutions-java-sdk</artifactId>
    <version>1.0.0-alpha</version>
</dependency>
```

### **Downloads**

If you are not using any of above, you will need to manually download and install the following JARs:

To use these JARs:

1. Download the JARs from [Jfrog](https://linkedin.jfrog.io/ui/native/talent-solutions-java-sdk/com/linkedin/talent-solutions-java-sdk/)
2. Add the JARs to your project's classpath

## **Documentation**

Please see the [API docs](https://learn.microsoft.com/en-us/linkedin/talent/?view=li-lts-2025-04) for the most up-to-date documentation.


## **SDK Overview**

We have grouped the api operations as per business use-case in separate clients.


### **ProvisioningClient**

This client encapsulates the LinkedIn provisioning APIs to manage customer profiles [here](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04).

Main Methods Exposed:



* [CreateApplication](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04#create-application)

  Created a child developer application

* [UpdateApplication](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04#update-application)

  Updates an existing child developer application

* [GetApplication](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04#get-application)

  Retrieves child developer application



### **JobPostingClient**

This client encapsulates the LinkedIn Job Posting APIs outlined [here](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/overview?view=li-lts-2025-04).

Main Methods Exposed:



* [ProcessJobPosting](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/sync-job-postings?view=li-lts-2025-04)

  Used to Create, Update, Upgrade, Downgrade, Close, or Renew a job posting.

* [GetTaskStatus](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-taskstatus?view=li-lts-2025-04)

  Retrieves LinkedInTaskStatus returned in response to a job posting action.

* [GetJobPostingStatus](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04)

  Retrieves JobPostingStatus using the externalJobPostingId.


### **P4PJobPostingClient**

  This client encapsulates the LinkedIn P4P(Pay For Performance) Job Posting and Reports APIs described [here](https://learn.microsoft.com/en-us/linkedin/talent/p4p-job-posting?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04).

  In addition to the methods provided by the JobPostingClient, the P4PJobPostingClient adds the following methods:



* [ProvisionCustomerHiringContracts](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/provision-customer-contracts?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04)

  Used to create provisioning hiring contract for P4P jobs

* [GetP4PReportsByIds](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#check-job-reports-by-ids)

  Retrieves P4PJobPostingPerformanceReports using identifiers such as externalJobPostingIds or partnerJobCode.

* [GetP4PReportsByDate](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#check-job-reports-by-date-range)

  Retrieves P4PJobPostingPerformanceReports for a specific date range.

* [GetPartnerBudgetReports](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#check-budget-reports)

  Retrieves PartnerBudgetReports for a given partner.


### **ApplyConnectJobPostingClient**

This client encapsulates the LinkedIn ApplyConnect Job Posting APIs described [here](https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/sync-jobs-onsite-apply?view=li-lts-2025-04)

 * [SyncJobApplicationNotification](https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/sync-job-application-feedback?view=li-lts-2025-04)
    
   Deliver real-time feedback to applicants

## **SDK Usage**


### **Use LinkedInClientFactory for Client Instance Creation**


#### ProvisioningClient instance

```java
ProvisioningClient provisioningClient = LinkedInClientFactory.getInstance().getProvisioningClient
        ("partner-clientid","partner-clientsecret");
```


#### JobPostingClient instance
```java
JobPostingClient jobPostingClient = LinkedInClientFactory.getInstance().getJobPostingClient
        ("customer-clientid", "customer-clientsecret");
```

#### P4PJobPostingClient instance
```java
P4PJobPostingClient p4pJobPostingClient = LinkedInClientFactory.getInstance().getP4PJobPostingClient
        ("customer-clientid", "customer-clientsecret");
```

#### ApplyConnectJobPostingClient instance
```java
ApplyConnectJobPostingClient applyConnectJobPostingClient = LinkedInClientFactory.getInstance().getApplyConnectJobPostingClient
        ("customer-clientid", "customer-clientsecret");
```

### **Basic JobPosting**

#### Create Basic JobPosting

```java
JobPosting jobPosting = JobPosting.builder()
        .externalJobPostingId("external job posting id")
        .companyId("companyId")
        .companyApplyUrl("https://careers.yourcompany.com")
        .jobPostingOperationType(JobPostingOperationType.CREATE)
        .title("Software Developer in Test")
        .description("Testing Job")
        .location("Sunnyvale, California")
        .listedAt(System.currentTimeMillis())
        .listingType(ListingType.BASIC)
        .employmentStatus(EmploymentStatus.FULL_TIME)
        .workplaceTypes(Arrays.asList(WorkplaceTypes.ON_SITE))
        .build();

// Build JobPostingRequest
JobPostingRequest jobPostingRequest = JobPostingRequest.builder()
        .elements(Arrays.asList(jobPosting))
        .build();

// Send JobPostingRequest using JobPostingClient.post().
APIResponse<JobPostingResponse> jobPostingResponse = jobPostingClient.post(jobPostingRequest);

```

#### Checking JobPosting Task Status

```java
APIResponse<JobTaskStatusResponse> jobTaskStatusResponse = jobPostingClient.getTaskStatus(Arrays.asList("linkedin task urn"));
```

#### Checking Job Status

```java
APIResponse<JobPostingStatus> jobPostingStatus = jobPostingClient.getJobPostingStatus(Arrays.asList("job posting id"));
```

### **P4P JobPosting**


#### Create P4P JobPosting

```java
// Build P4PBudget instance for P4P JobPosting Request

P4PBudget p4PBudget = P4PBudget.builder()
.payForPerformanceTotalBudget(MoneyAmount.builder().amount("5.00").currencyCode(CurrencyCode.USD).build())
    .build();

JobPosting jobPosting = JobPosting.builder()
        .externalJobPostingId("external job posting id")
        .companyId("companyId")
        .companyApplyUrl("https://careers.yourcompany.com")
        .contract("urn:li:contract:{your-contract_id}")
       .companyJobCode(
"ATS-source-requisition_id_or_externalJobPostingId")
        .jobPostingOperationType(JobPostingOperationType.CREATE)
        .title("Software Developer in Test")
        .description("Testing Job")
        .location("Sunnyvale, California")
        .listedAt(System.currentTimeMillis())
        .listingType(ListingType.BASIC)
        .employmentStatus(EmploymentStatus.FULL_TIME)
        .workplaceTypes(Arrays.asList(WorkplaceTypes.ON_SITE))
        .p4PBudget(p4pBudget)
        .build();

// Build JobPostingRequest
JobPostingRequest jobPostingRequest = JobPostingRequest.builder()
        .elements(Arrays.asList(jobPosting))
        .build();

// Send JobPostingRequest using JobPostingClient.post().
APIResponse<JobPostingResponse> jobPostingResponse = p4pJobPostingClient.post(jobPostingRequest);
```
#### The taskStatus and jobPostingStatus handling for P4P Job Postings remains consistent with that of basic job postings. However, all related operations will be performed using the P4PJobPostingClient.


### Retrieve P4PJobPerformanceReports By Ids

```java
P4PJobReportsRequestByIds p4PJobReportsRequestByIds = P4PJobReportsRequestByIds
    .builder()
    .ids(Arrays.asList("external job posting id"))
    .dateRange(DateRange.builder().start(Date.builder().day(10).month(05).year(2025).build()).end(Date.builder().day(12).month(05).year(2025).build()).build())
    .partnerContractId("enter partner contract id")
    .build();

APIResponse<P4PReportResponseByIds> p4PReportResponseByIds = p4pJobPostingClient.getP4PReportByIds(p4PJobReportsRequestByIds);
```

### Retrieve P4PJobPerformanceReports By Date

```java
P4PJobReportsRequestByDate p4PJobReportsRequestByDate = P4PJobReportsRequestByDate
    .builder()
    .dateRange(DateRange.builder().start(Date.builder().day(10).month(05).year(2025).build()).end(Date.builder().day(12).month(05).year(2025).build()).build())
    .partnerContractId("enter partner contract id")
    .build();

APIResponse<P4PReportResponseByDate> p4PReportResponseByDate = p4pJobPostingClient.getP4PReportsByDate(p4PJobReportsRequestByDate);
```

### Retrieve Budget Reports

```java
APIResponse<P4PBudgetReportResponse> p4PBudgetReportResponse= p4pJobPostingClient.getPartnerBudgetReports("enter partner contract id");
```

### **ApplyConnect JobPosting**


#### Create JobPosting with ApplyConnect

```java
// Build OnsiteApplyConfiugration for ApplyConnect JobPosting. You have to customize SimpleTalentQuestions as per your need.

OnsiteApplyConfiguration onsiteApplyConfiguration = OnsiteApplyConfiguration
    .builder()
    .jobApplicationWebhookUrl("https://yourcompany.com/webhook")
    .questions(SimpleTalentQuestions.builder()
        .additionalQuestions(additionalQuestions)
        .build())
    .build();

JobPosting jobPosting = JobPosting.builder()
        .externalJobPostingId("external job posting id")
        .companyId("companyId")
        .companyApplyUrl("https://careers.yourcompany.com")
        .jobPostingOperationType(JobPostingOperationType.CREATE)
        .title("Software Developer in Test")
        .description("Testing Job")
        .location("Sunnyvale, California")
        .listedAt(System.currentTimeMillis())
        .listingType(ListingType.BASIC)
        .employmentStatus(EmploymentStatus.FULL_TIME)
        .workplaceTypes(Arrays.asList(WorkplaceTypes.ON_SITE))
        .onsiteApplyConfiguration(onsiteApplyConfiguration)
        .build();

// Build JobPostingRequest
JobPostingRequest jobPostingRequest = JobPostingRequest.builder()
        .elements(Arrays.asList(jobPosting))
        .build();

// Send JobPostingRequest using JobPostingClient.post().
APIResponse<JobPostingResponse> jobPostingResponse = applyConnectJobPostingClient.post(jobPostingRequest);
```

#### The taskStatus and jobPostingStatus handling for ApplyConnect Job Postings remains consistent with that of basic job postings. However, all related operations should be performed using the P4PJobPostingClient.
