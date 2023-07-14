# estudos-threads
Estudos sobre threads

## Virtual Threads ##

#### Cooperative Scheduling ####

-Djdk.virtualThreadScheduler.parallelism=1
-Djdk.virtualThreadScheduler.maxPoolSize=1
-Djdk.virtualThreadScheduler.minRunnable=1

Ao rodar o método runWithCooperativeScheduling é possível ver a troca de tarefas executada no worker, pelo fato da vThread de workingForever estar executando infinitamente e nunca finalizar.

Agora altere as propriedades do sistema para:

-Djdk.virtualThreadScheduler.parallelism=2
-Djdk.virtualThreadScheduler.maxPoolSize=2
-Djdk.virtualThreadScheduler.minRunnable=2

Rode o comando runPinedVirtualThreads para ver que as execuções serão executados em cada "worker".
#### Pinned Virtual Threads  #####

-Djdk.virtualThreadScheduler.parallelism=1
-Djdk.virtualThreadScheduler.maxPoolSize=2
-Djdk.virtualThreadScheduler.minRunnable=1

Rodando o método runWithPinnedVirtualThreads é possível ver que o as tarefas irão ser executadas em workers diferentes, pois um está "bloqueado". Ou seja, a gente "fixou" a nossa vThread em uma Thread de plataforma.

Obs: compare com o primeiro caso dos exemplos da vThread que conseguirá ver a diferença de ter uma tarefa com bloqueio ou não.

